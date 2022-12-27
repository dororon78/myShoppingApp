package com.example.myshoppingapp.data.shoppingitem

data class ShoppingItemDto(
    val id: String = "",
    val name: String = "",
    val amount: Int? = null,
    val unit: String? = null,
    val checked: Boolean = false,
)