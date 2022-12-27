package com.example.myshoppingapp.presentation.viewcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingapp.presentation.theme.clickableColor
import com.example.myshoppingapp.presentation.theme.notClickableColor
import com.example.myshoppingapp.presentation.viewmodels.BottomSheetState
import com.example.myshoppingapp.presentation.viewmodels.BottomSheetViewModel
import com.example.myshoppingapp.presentation.viewmodels.ShoppingListViewModel

@Composable
fun MenuButton(
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(),
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
) {
    val existCheckItems by shoppingListViewModel.existCheckedItem.observeAsState(false)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        MenuTextButton(
            text = "추가", testTag = "add_item_button",
            onClick = { bottomSheetViewModel.setBottomSheetState(BottomSheetState.ItemAddForm) }
        )
        MenuTextButton(
            text = "삭제",
            enable = existCheckItems,
            testTag = "remove_item_button",
            onClick = { shoppingListViewModel.removeCheckedItems() }
        )
        MenuTextButton(
            text = if (existCheckItems) "완료 및 결제" else "결제",
            testTag = "done_shopping_button",
            onClick = {
                shoppingListViewModel.removeCheckedItems()
                bottomSheetViewModel.setBottomSheetState(BottomSheetState.ExternalAppList)
            }
        )
    }
}

@Composable
fun RowScope.MenuTextButton(
    text: String,
    enable: Boolean = true,
    testTag: String = "",
    onClick: () -> Unit,
) {
    val menuButton = TextStyle(
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .weight(1f, false)
            .clickable(enabled = enable, onClick = onClick)
            .testTag(testTag),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            style = menuButton.copy(
                color = if (enable) clickableColor else notClickableColor
            )
        )
    }
}

