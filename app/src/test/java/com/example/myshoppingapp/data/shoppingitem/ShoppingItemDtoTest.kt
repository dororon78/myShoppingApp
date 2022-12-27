package com.example.myshoppingapp.data.shoppingitem

import org.junit.Assert.assertEquals
import org.junit.Test


internal class ShoppingItemDtoTest {

    private lateinit var shoppingItemDto: ShoppingItemDto

    @Test
    fun getId_CreateInstanceWithId_ReturnsOriginal() {
        shoppingItemDto = ShoppingItemDto(
            id = ITEM_ID,
            name = ITEM_NAME,
        )

        assertEquals(
            ITEM_ID,
            shoppingItemDto.id
        )
    }

    @Test
    fun getName_CreateInstanceWithName_ReturnsOriginal() {
        shoppingItemDto = ShoppingItemDto(
            id = ITEM_ID,
            name = ITEM_NAME,
        )

        assertEquals(
            ITEM_NAME,
            shoppingItemDto.name
        )
    }

    @Test
    fun getChecked_CreateInstanceWithChecked_ReturnsOriginal() {
        shoppingItemDto = ShoppingItemDto(
            id = ITEM_ID,
            name = ITEM_NAME,
            checked = ITEM_CHECKED
        )

        assertEquals(
            ITEM_CHECKED,
            shoppingItemDto.checked
        )
    }

    @Test
    fun getChecked_CreateInstanceWithoutChecked_ReturnsFalseAsDefault() {
        shoppingItemDto = ShoppingItemDto(
            id = ITEM_ID,
            name = ITEM_NAME,
        )

        assertEquals(
            false,
            shoppingItemDto.checked
        )
    }


    @Test
    fun copy_CreateInstanceWithArguments_ReturnsOriginals() {
        shoppingItemDto = ShoppingItemDto(
            id = ITEM_ID,
            name = ITEM_NAME,
            checked = ITEM_CHECKED
        )

        val result = shoppingItemDto.copy()

        assertEquals(
            shoppingItemDto.id,
            result.id
        )
        assertEquals(
            shoppingItemDto.name,
            result.name
        )
        assertEquals(
            shoppingItemDto.checked,
            result.checked
        )
    }


    companion object {
        private const val ITEM_ID = "ITEM_ID"
        private const val ITEM_NAME = "ITEM_NAME"
        private const val ITEM_CHECKED = true
    }
}