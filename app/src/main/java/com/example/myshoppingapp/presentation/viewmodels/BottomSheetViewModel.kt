package com.example.myshoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class BottomSheetState {
    GONE,
    ItemAddForm,
    ExternalAppList,
}

@HiltViewModel
class BottomSheetViewModel @Inject internal constructor(
) : ViewModel() {
    private val _bottomSheetState: MutableLiveData<BottomSheetState> = MutableLiveData(
        BottomSheetState.GONE
    )
    val bottomSheetState: LiveData<BottomSheetState> = _bottomSheetState

    fun setBottomSheetState(state: BottomSheetState) {
        _bottomSheetState.value = state
    }
}