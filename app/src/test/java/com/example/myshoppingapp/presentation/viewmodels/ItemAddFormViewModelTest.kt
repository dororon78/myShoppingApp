package com.example.myshoppingapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.usecases.AppendShoppingItemUseCase
import com.example.myshoppingapp.domain.usecases.CheckItemDuplicationUseCase
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ItemAddFormViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var itemAddFormViewModel: ItemAddFormViewModel

    private val mockShoppingItem: ShoppingItem = mockk()
    private val appendShoppingItemUseCase: AppendShoppingItemUseCase = mockk()
    private val checkItemDuplicationUseCase: CheckItemDuplicationUseCase = mockk()

    @Before
    fun setUp() {
        itemAddFormViewModel =
            ItemAddFormViewModel(appendShoppingItemUseCase, checkItemDuplicationUseCase)
    }

    @Test
    fun getTitle_InitializedViewModel_ReturnEmptyStringAsDefault() {
        val result = itemAddFormViewModel.title.value

        assertEquals(
            "",
            result
        )
    }

    @Test
    fun getComment_InitializedViewModel_ReturnEmptyStringAsDefault() {
        val result = itemAddFormViewModel.comment.value

        assertEquals(
            "",
            result
        )
    }

    @Test
    fun getAdvancedSettingVisibility_InitializedViewModel_ReturnFalseAsDefault() {
        val result = itemAddFormViewModel.advancedSettingVisibility.value

        assertEquals(
            false,
            result
        )
    }

    @Test
    fun getAmount_InitializedViewModel_ReturnZeroAsDefault() {
        val result = itemAddFormViewModel.amount.value

        assertEquals(
            0,
            result
        )
    }

    @Test
    fun getUnit_InitializedViewModel_ReturnEmptyStringAsDefault() {
        val result = itemAddFormViewModel.unit.value

        assertEquals(
            "",
            result
        )
    }

    @Test
    fun createItem_ValidDataAndNoDuplication_SucceedSaveAndClearForm() {
        val testName = "TestName"
        every { checkItemDuplicationUseCase.invoke(testName) } returns false
        every { mockShoppingItem.name } returns testName
        every { appendShoppingItemUseCase.invoke(mockShoppingItem) } just runs

        itemAddFormViewModel.createItem(mockShoppingItem)

        verify { itemAddFormViewModel.createItem(mockShoppingItem) }

        itemAddFormViewModel.title.value.let {
            assertEquals(
                "",
                it
            )
        }
        itemAddFormViewModel.amount.value.let {
            assertEquals(
                0,
                it
            )
        }
        itemAddFormViewModel.unit.value.let {
            assertEquals(
                "",
                it
            )
        }
    }

    @Test
    fun createItem_ValidDataAndDuplication_FailSave() {
        val testName = "TestName"
        every { mockShoppingItem.name } returns testName
        every { checkItemDuplicationUseCase.invoke(any()) } returns true

        itemAddFormViewModel.createItem(mockShoppingItem)

        verify { checkItemDuplicationUseCase.invoke(testName) }
        verify(exactly = 0) { appendShoppingItemUseCase.invoke(mockShoppingItem) }
        verify(exactly = 0) { appendShoppingItemUseCase.invoke(mockShoppingItem) }
    }

    @Test
    fun createItem3_InvalidData_FailSave() {
        val emptyTestName = ""
        every { mockShoppingItem.name } returns emptyTestName

        itemAddFormViewModel.createItem(mockShoppingItem)

        verify(exactly = 0) { checkItemDuplicationUseCase.invoke(emptyTestName) }
        verify(exactly = 0) { appendShoppingItemUseCase.invoke(mockShoppingItem) }
    }

    @Test
    fun clearForm_SetAllAsDefaultValue() {
        itemAddFormViewModel.clearForm()
        itemAddFormViewModel.title.value.let {
            assertEquals(
                "",
                it
            )
        }
        itemAddFormViewModel.amount.value.let {
            assertEquals(
                0,
                it
            )
        }
        itemAddFormViewModel.unit.value.let {
            assertEquals(
                "",
                it
            )
        }
    }

    @Test
    fun updateTitle_ReturnSameValue() {
        val newTitle = "newTitle"
        itemAddFormViewModel.updateTitle(newTitle)
        itemAddFormViewModel.title.value.let {
            assertEquals(
                newTitle,
                it
            )
        }
    }


    @Test
    fun updateAmount_DigitString_ReturnStringAsInt() {
        val newAmount = "1"
        itemAddFormViewModel.updateAmount(newAmount)
        itemAddFormViewModel.amount.value.let {
            assertEquals(
                1,
                it
            )
        }
    }

    @Test
    fun updateAmount_EmptyString_ReturnZero() {
        val newAmount = ""
        itemAddFormViewModel.updateAmount(newAmount)
        itemAddFormViewModel.amount.value.let {
            assertEquals(
                0,
                it
            )
        }
    }


    @Test
    fun updateAmount_NoDigitTitle_UpdateAny() {
        val newAmount = "a"
        itemAddFormViewModel.updateAmount(newAmount)
        itemAddFormViewModel.amount.value.let {
            assertEquals(
                0,
                it
            )
        }
    }


    @Test
    fun updateUnit_ReturnSameValue() {
        val newUnit = "newTitle"
        itemAddFormViewModel.updateUnit(newUnit)
        itemAddFormViewModel.unit.value.let {
            assertEquals(
                newUnit,
                it
            )
        }
    }

    @Test
    fun toggleAdvancedSettingVisibility_ReturnToggledValue() {
        val old = itemAddFormViewModel.advancedSettingVisibility.value

        old?.let {
            itemAddFormViewModel.toggleAdvancedSettingVisibility(it)
            val new = itemAddFormViewModel.advancedSettingVisibility.value

            assertEquals(
                !it,
                new
            )
        }
    }
}