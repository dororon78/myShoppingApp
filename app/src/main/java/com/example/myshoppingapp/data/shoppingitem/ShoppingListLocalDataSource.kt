package com.example.myshoppingapp.data.shoppingitem

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class ShoppingListLocalDataSource @Inject internal constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val shoppingListProcessor = BehaviorProcessor.create<List<ShoppingItemDto>>()

    init {
        updateShoppingList()
    }

    fun getShoppingListObservable(): Observable<List<ShoppingItemDto>> {
        return shoppingListProcessor.toObservable()
    }

    fun loadShoppingList(): List<ShoppingItemDto> {
        val serializedShoppingMap = sharedPreferences.all as Map<String, String>
        val deserializing = { json: String ->
            Gson().fromJson(json, ShoppingItemDto::class.java)
        }
        return serializedShoppingMap.map { (_, v) -> deserializing(v) }
    }

    private fun updateShoppingList() {
        Single.create {
            it.onSuccess(loadShoppingList())
        }
            .observeOn(Schedulers.io())
            .doOnSuccess {
                shoppingListProcessor.onNext(it)
            }
            .subscribe()
    }

    fun saveShoppingItem(dto: ShoppingItemDto) {
        val json: String = Gson().toJson(dto)
        sharedPreferences.edit().putString(dto.id, json).apply()
        updateShoppingList()
    }

    fun removeShoppingItem(id: String) {
        sharedPreferences.edit().remove(id).apply()
        updateShoppingList()
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "shopping_list"
    }
}