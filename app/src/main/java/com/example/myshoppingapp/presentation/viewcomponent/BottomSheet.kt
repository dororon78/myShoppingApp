package com.example.myshoppingapp.presentation.viewcomponent

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingapp.presentation.theme.AnimatedBottomSheet
import com.example.myshoppingapp.presentation.theme.AnimatedBottomSheetBackground
import com.example.myshoppingapp.presentation.theme.bottomSheetBackgroundColor
import com.example.myshoppingapp.presentation.viewmodels.BottomSheetState
import com.example.myshoppingapp.presentation.viewmodels.BottomSheetViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheet(
    viewModel: BottomSheetViewModel = hiltViewModel()
) {
    val consumeTapEvent = {}
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomSheetState by viewModel.bottomSheetState.observeAsState(BottomSheetState.GONE)

    AnimatedBottomSheetBackground(
        visible = (bottomSheetState != BottomSheetState.GONE)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bottomSheetBackgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures {
                        viewModel.setBottomSheetState(BottomSheetState.GONE)
                        keyboardController?.hide()
                    }
                }
                .testTag("bottom_sheet_scrim"),
            content = {
                BackHandler((bottomSheetState != BottomSheetState.GONE)) {
                    viewModel.setBottomSheetState(
                        BottomSheetState.GONE
                    )
                }
            }
        )
    }

    AnimatedBottomSheet(
        visible = (bottomSheetState != BottomSheetState.GONE)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .animateContentSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        consumeTapEvent()
                    }
                },
        ) {
            when (bottomSheetState) {
                BottomSheetState.GONE -> {}
                BottomSheetState.ItemAddForm -> {
                    ItemAddForm()
                }
                BottomSheetState.ExternalAppList -> {
                    ExternalAppList()
                }
            }
        }
    }
}