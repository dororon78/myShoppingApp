package com.example.myshoppingapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.usecases.AppendShoppingItemUseCase
import com.example.myshoppingapp.domain.usecases.CheckItemDuplicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemAddFormViewModel @Inject internal constructor(
    private val appendShoppingItemUseCase: AppendShoppingItemUseCase,
    private val checkItemDuplicationUseCase: CheckItemDuplicationUseCase,
) : ViewModel() {
    private val _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _comment: MutableLiveData<String> = MutableLiveData("")
    val comment: LiveData<String> = _comment

    private val _advancedSettingVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val advancedSettingVisibility: LiveData<Boolean> = _advancedSettingVisibility

    private val _amount: MutableLiveData<Int> = MutableLiveData(0)
    val amount: LiveData<Int> = _amount

    private val _unit: MutableLiveData<String> = MutableLiveData("")
    val unit: LiveData<String> = _unit

    fun createItem(item: ShoppingItem) {
        if (item.name.isNotEmpty()) {
            val existAlready = checkItemDuplicationUseCase(item.name)
            if (existAlready) {
                _comment.value = "이미 등록된 이름입니다."
            } else {
                appendShoppingItemUseCase(item)
                clearForm()
            }
        }
    }

    fun clearForm() {
        _title.value = ""
        _comment.value = ""
        _amount.value = 0
        _unit.value = ""
    }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateAmount(amount: String) {
        if (amount.isBlank()) {
            _amount.value = 0
        } else {
            val intAmount = amount.toIntOrNull()
            if (intAmount != null) {
                _amount.value = intAmount
            }
        }
    }

    fun updateUnit(unit: String) {
        _unit.value = unit
    }

    fun toggleAdvancedSettingVisibility(advancedSettingVisibility: Boolean) {
        _advancedSettingVisibility.value = !advancedSettingVisibility
    }
}