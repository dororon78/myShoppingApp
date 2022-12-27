package com.example.myshoppingapp.data.shoppingitem

import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.repository.ShoppingListRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject internal constructor(
    private val shoppingListLocalDataSource: ShoppingListLocalDataSource,
    private val mapper: ShoppingItemModelMapper
) : ShoppingListRepository {

    override fun getShoppingListObservable(): Observable<List<ShoppingItem>> =
        shoppingListLocalDataSource.getShoppingListObservable()
            .map { list -> list.map { mapper.toDomainLayer(it) } }

    override fun getShoppingList(): List<ShoppingItem> {
        return shoppingListLocalDataSource.loadShoppingList().map { mapper.toDomainLayer(it) }
    }

    override fun saveShoppingItem(item: ShoppingItem) {
        shoppingListLocalDataSource.saveShoppingItem(mapper.fromDomainLayer(item))
    }

    override fun removeShoppingItem(id: String) {
        shoppingListLocalDataSource.removeShoppingItem(id)
    }

    override fun updateShoppingItem(item: ShoppingItem) {
        shoppingListLocalDataSource.saveShoppingItem(mapper.fromDomainLayer(item))
    }
}