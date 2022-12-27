package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetShoppingListObservableUseCase @Inject internal constructor(
    private val repository: ShoppingListRepository
) {
    operator fun invoke(): Observable<List<ShoppingItem>> {
        return repository.getShoppingListObservable()
    }
}