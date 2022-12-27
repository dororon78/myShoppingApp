package com.example.myshoppingapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class BottomSheetViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var bottomSheetViewModel: BottomSheetViewModel

    @Before
    fun setUp() {
        bottomSheetViewModel = BottomSheetViewModel()
    }

    @Test
    fun getBottomSheetState_InitializedViewModel_ReturnGoneAsDefault() {
        val result = bottomSheetViewModel.bottomSheetState.value

        assertEquals(
            BottomSheetState.GONE,
            result
        )
    }

    @Test
    fun setBottomSheetState_WithGone_ReturnGone() {
        bottomSheetViewModel.setBottomSheetState(BottomSheetState.GONE)
        val result = bottomSheetViewModel.bottomSheetState.value

        assertEquals(
            BottomSheetState.GONE,
            result
        )
    }

    @Test
    fun setBottomSheetState_WithExternalAppList_ReturnExternalAppList() {
        bottomSheetViewModel.setBottomSheetState(BottomSheetState.ExternalAppList)

        val result = bottomSheetViewModel.bottomSheetState.value

        assertEquals(
            BottomSheetState.ExternalAppList,
            result
        )
    }

    @Test
    fun setBottomSheetState_WithItemAddForm_ReturnItemAddForm() {
        bottomSheetViewModel.setBottomSheetState(BottomSheetState.ItemAddForm)
        val result = bottomSheetViewModel.bottomSheetState.value

        assertEquals(
            BottomSheetState.ItemAddForm,
            result
        )
    }
}
