package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import javax.inject.Inject

class RemoveShoppingItemUseCase @Inject internal constructor(
    private val repository: ShoppingListRepository
) {
    operator fun invoke(items: List<String>) {
        for (item in items) {
            repository.removeShoppingItem(item)
        }
    }
}