package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import javax.inject.Inject

class AppendShoppingItemUseCase @Inject internal constructor(
    private val repository: ShoppingListRepository
) {
    operator fun invoke(item: ShoppingItem) {
        repository.saveShoppingItem(item)
    }
}