package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class CheckItemDuplicationUseCaseTest {

    private lateinit var checkItemDuplicationUseCase: CheckItemDuplicationUseCase

    private val mockExistShoppingItem: ShoppingItem = mockk()
    private val mockShoppingListRepository: ShoppingListRepository = mockk()

    @Before
    fun setUp() {
        every { mockExistShoppingItem.name } returns TEST_NAME

        checkItemDuplicationUseCase = CheckItemDuplicationUseCase(mockShoppingListRepository)
    }

    @Test
    fun invoke_ExistNameInList_ReturnTrue() {
        every { mockShoppingListRepository.getShoppingList() } returns listOf(mockExistShoppingItem)

        val result = checkItemDuplicationUseCase.invoke(TEST_NAME)

        assertEquals(
            true,
            result
        )
        verify { mockShoppingListRepository.getShoppingList() }
    }

    @Test
    fun invoke_EmptyList_ReturnFalse() {
        every { mockShoppingListRepository.getShoppingList() } returns listOf(mockExistShoppingItem)

        val result = checkItemDuplicationUseCase.invoke(NOT_EXIST_TEST_NAME)

        assertEquals(
            false,
            result
        )
        verify { mockShoppingListRepository.getShoppingList() }
    }

    @Test
    fun invoke_NotExistNameInList_InvokeRepositoryWithSameValue() {
        every { mockShoppingListRepository.getShoppingList() } returns listOf()

        val result = checkItemDuplicationUseCase.invoke(NOT_EXIST_TEST_NAME)

        assertEquals(
            false,
            result
        )
        verify { mockShoppingListRepository.getShoppingList() }
    }

    companion object {
        const val TEST_NAME = "TEST_NAME"
        const val NOT_EXIST_TEST_NAME = "NOT_EXIST_TEST_NAME"
    }
}