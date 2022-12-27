package com.example.myshoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.usecases.GetShoppingListObservableUseCase
import com.example.myshoppingapp.domain.usecases.RemoveShoppingItemUseCase
import com.example.myshoppingapp.domain.usecases.UpdateShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject


@HiltViewModel
class ShoppingListViewModel @Inject internal constructor(
    private val removeShoppingItemUseCase: RemoveShoppingItemUseCase,
    private val getShoppingListObservableUseCase: GetShoppingListObservableUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
) : ViewModel() {
    private val _shoppingItems: MutableLiveData<List<ShoppingItem>> = MutableLiveData(listOf())
    val shoppingItems: LiveData<List<ShoppingItem>> = _shoppingItems

    private val _existCheckedItem: MutableLiveData<Boolean> = MutableLiveData(false)
    val existCheckedItem: LiveData<Boolean> = _existCheckedItem

    init {
        subscribeShoppingList()
    }

    private fun subscribeShoppingList() {
        getShoppingListObservableUseCase.invoke()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { list ->
                _shoppingItems.value = list

                val exist = list.firstOrNull { item -> item.checked } != null
                _existCheckedItem.value = exist
            }
            .subscribe({}, {})
    }

    fun toggleChecked(item: ShoppingItem) {
        updateShoppingItemUseCase(item.toggleChecked())
    }

    fun removeCheckedItems() {
        _shoppingItems.value
            ?.filter { it.checked }
            ?.map { it.id }
            ?.run {
                removeShoppingItemUseCase(this)
            }
    }
}