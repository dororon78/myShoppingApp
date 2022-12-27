package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

internal class GetShoppingListObservableUseCaseTest {

    private lateinit var getShoppingListObservableUseCase: GetShoppingListObservableUseCase

    private val mockShoppingListRepository: ShoppingListRepository = mockk()
    private val mockObservable: Observable<List<ShoppingItem>> = mockk()

    @Before
    fun setUp() {
        getShoppingListObservableUseCase =
            GetShoppingListObservableUseCase(mockShoppingListRepository)
    }

    @Test
    fun invoke_InvokeRepositoryWithSameValue() {
        every { mockShoppingListRepository.getShoppingListObservable() } returns mockObservable

        getShoppingListObservableUseCase.invoke()

        verify { mockShoppingListRepository.getShoppingListObservable() }
    }
}