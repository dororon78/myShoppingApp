package com.example.myshoppingapp.data.shoppingitem

import com.example.myshoppingapp.domain.ShoppingItem
import javax.inject.Inject

class ShoppingItemModelMapper @Inject internal constructor(
) {
    fun toDomainLayer(dto: ShoppingItemDto): ShoppingItem {
        return ShoppingItem(
            id = dto.id,
            name = dto.name,
            amount = dto.amount,
            unit = dto.unit,
            checked = dto.checked,
        )
    }

    fun fromDomainLayer(item: ShoppingItem): ShoppingItemDto {
        return ShoppingItemDto(
            id = item.id,
            name = item.name,
            amount = item.amount,
            unit = item.unit,
            checked = item.checked,
        )
    }
}