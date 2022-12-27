package com.example.myshoppingapp.domain.usecases

import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import javax.inject.Inject

class CheckItemDuplicationUseCase @Inject internal constructor(
    private val repository: ShoppingListRepository
) {
    operator fun invoke(title: String): Boolean {
        return repository.getShoppingList().firstOrNull { it.name == title } != null
    }
}