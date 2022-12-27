package com.example.myshoppingapp.data.shoppingitem

import com.example.myshoppingapp.domain.ShoppingItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


internal class ShoppingItemModelMapperTest {

    private lateinit var shoppingItemModelMapper: ShoppingItemModelMapper

    private val mockShoppingItemDto: ShoppingItemDto = mockk()
    private val mockShoppingItem: ShoppingItem = mockk()

    @Before
    fun setUp() {
        every { mockShoppingItemDto.id } returns ITEM_ID
        every { mockShoppingItemDto.name } returns ITEM_NAME
        every { mockShoppingItemDto.checked } returns ITEM_CHECKED
        every { mockShoppingItemDto.amount } returns ITEM_AMOUNT
        every { mockShoppingItemDto.unit } returns ITEM_UNIT

        every { mockShoppingItem.id } returns ITEM_ID
        every { mockShoppingItem.name } returns ITEM_NAME
        every { mockShoppingItem.checked } returns ITEM_CHECKED
        every { mockShoppingItem.amount } returns ITEM_AMOUNT
        every { mockShoppingItem.unit } returns ITEM_UNIT

        shoppingItemModelMapper = ShoppingItemModelMapper()
    }

    @Test
    fun toDomainLayer_MappingToDomainModel_ReturnsWithSameValue() {
        val result = shoppingItemModelMapper.toDomainLayer(mockShoppingItemDto)

        assertEquals(
            ITEM_ID,
            result.id
        )

        assertEquals(
            ITEM_NAME,
            result.name
        )

        assertEquals(
            ITEM_CHECKED,
            result.checked
        )
    }

    @Test
    fun fromDomainLayer_MappingFromDomainModel_ReturnsWithSameValue() {
        val result = shoppingItemModelMapper.fromDomainLayer(mockShoppingItem)

        assertEquals(
            ITEM_ID,
            result.id
        )

        assertEquals(
            ITEM_NAME,
            result.name
        )

        assertEquals(
            ITEM_CHECKED,
            result.checked
        )
    }

    companion object {
        private const val ITEM_ID = "ITEM_ID"
        private const val ITEM_NAME = "ITEM_NAME"
        private const val ITEM_CHECKED = true
        private const val ITEM_AMOUNT = 0
        private const val ITEM_UNIT = "ITEM_UNIT"
    }
}