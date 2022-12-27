package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.mockk.*
import org.junit.Before
import org.junit.Test

internal class RemoveShoppingItemUseCaseTest {

    private lateinit var removeShoppingItemUseCase: RemoveShoppingItemUseCase

    private val mockShoppingListRepository: ShoppingListRepository = mockk()


    @Before
    fun setUp() {
        removeShoppingItemUseCase = RemoveShoppingItemUseCase(mockShoppingListRepository)
    }

    @Test
    fun invoke_VerifyInvocationItemsAsSizeOfList() {
        every { mockShoppingListRepository.removeShoppingItem(any()) } just runs

        val list = listOf("id1", "id2")
        removeShoppingItemUseCase.invoke(list)

        verify(exactly = list.size) { mockShoppingListRepository.removeShoppingItem(any()) }
    }
}