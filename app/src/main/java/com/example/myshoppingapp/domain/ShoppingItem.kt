package com.example.myshoppingapp.domain

import java.util.*

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: Int? = null,
    val unit: String? = null,
    val checked: Boolean = false,
) {
    override fun toString(): String {
        val amount = when (amount) {
            null, 0 -> ""
            else -> amount.toString()
        }
        return "$name $amount${unit ?: ""}"
    }

    fun toggleChecked(): ShoppingItem {
        return this.copy(checked = !checked)
    }
}
