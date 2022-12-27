package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.mockk.*
import org.junit.Before
import org.junit.Test

internal class AppendShoppingItemUseCaseTest {

    private lateinit var appendShoppingItemUseCase: AppendShoppingItemUseCase

    private val mockShoppingItem: ShoppingItem = mockk()
    private val mockShoppingListRepository: ShoppingListRepository = mockk()

    @Before
    fun setUp() {
        appendShoppingItemUseCase = AppendShoppingItemUseCase(mockShoppingListRepository)
    }

    @Test
    fun invoke_InvokeRepositoryWithSameValue() {
        every { mockShoppingListRepository.saveShoppingItem(mockShoppingItem) } just runs

        appendShoppingItemUseCase.invoke(mockShoppingItem)

        verify { mockShoppingListRepository.saveShoppingItem(mockShoppingItem) }
    }
}