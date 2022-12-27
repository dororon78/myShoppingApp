package com.example.myshoppingapp.domain.repository

import com.example.myshoppingapp.domain.ShoppingItem
import io.reactivex.rxjava3.core.Observable

interface ShoppingListRepository {
    fun getShoppingListObservable(): Observable<List<ShoppingItem>>
    fun getShoppingList(): List<ShoppingItem>
    fun saveShoppingItem(item: ShoppingItem)
    fun removeShoppingItem(id: String)
    fun updateShoppingItem(item: ShoppingItem)
}