package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.mockk.*
import org.junit.Before
import org.junit.Test


internal class UpdateShoppingItemUseCaseTest {

    private lateinit var updateShoppingItemUseCase: UpdateShoppingItemUseCase

    private val mockShoppingItem: ShoppingItem = mockk()
    private val mockShoppingListRepository: ShoppingListRepository = mockk()

    @Before
    fun setUp() {
        updateShoppingItemUseCase = UpdateShoppingItemUseCase(mockShoppingListRepository)
    }

    @Test
    fun invoke_InvokeRepositoryWithSameValue() {
        every { mockShoppingListRepository.updateShoppingItem(mockShoppingItem) } just runs

        updateShoppingItemUseCase.invoke(mockShoppingItem)

        verify { mockShoppingListRepository.updateShoppingItem(mockShoppingItem) }
    }
}