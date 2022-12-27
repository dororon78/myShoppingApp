package com.example.myshoppingapp.data.shoppingitem

import android.content.Context
import android.content.SharedPreferences
import io.mockk.*
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ShoppingListLocalDataSourceTest {

    private lateinit var shoppingListLocalDataSource: ShoppingListLocalDataSource

    private val mockContext: Context = mockk()
    private val mockSharedPreferences: SharedPreferences = mockk()
    private val mockSharedPreferencesEditor: SharedPreferences.Editor = mockk()
    private val mockMap: Map<String, String> = mockk()

    @Before
    fun setUp() {
        every {
            mockContext.getSharedPreferences(
                ShoppingListLocalDataSource.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockSharedPreferencesEditor
        every {
            mockSharedPreferencesEditor.putString(
                any(),
                any()
            )
        } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.remove(any()) } returns mockSharedPreferencesEditor
        every { mockSharedPreferencesEditor.apply() } just runs
    }

    private fun setNormalData() {
        val key = EXIST_ITEM_ID
        val value =
            "{\"checked\":${EXIST_ITEM_CHECKED},\"id\":\"${EXIST_ITEM_ID}\",\"name\":\"${EXIST_ITEM_NAME}\"}"
        every { mockSharedPreferences.all } returns mapOf(key to value)
    }

    private fun setEmptyData() {
        every { mockSharedPreferences.all } returns mapOf<String, Any>()
    }

    private fun setAbnormalDataWithExtraField() {
        val key = EXIST_ITEM_ID
        val value =
            "{\"checked\":${EXIST_ITEM_CHECKED},\"id\":\"${EXIST_ITEM_ID}\",\"name\":\"${EXIST_ITEM_NAME}\",\"CustomField\":\"Custom\"}"
        every { mockSharedPreferences.all } returns mapOf(key to value)
    }

    private fun setAbnormalDataWithoutSomeField() {
        val key = EXIST_ITEM_ID
        val value = "{\"checked\":${EXIST_ITEM_CHECKED},\"id\":\"${EXIST_ITEM_ID}\"}"
        every { mockSharedPreferences.all } returns mapOf(key to value)
    }

    @Test
    fun getShoppingListObservable_InitWithNormalData_ReturnListOfShoppingItemDto() {
        setNormalData()
        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)

        shoppingListLocalDataSource.getShoppingListObservable()
            .concatWith(Observable.never())
            .test()
            .assertNoErrors()
            .assertValue(
                listOf(
                    ShoppingItemDto(
                        id = EXIST_ITEM_ID,
                        name = EXIST_ITEM_NAME,
                        checked = EXIST_ITEM_CHECKED
                    )
                )
            )
    }

    @Test
    fun loadShoppingList_InitWithNormalData_ReturnListOfShoppingItemDto() {
        setNormalData()
        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)

        val result = shoppingListLocalDataSource.loadShoppingList()

        assertEquals(
            listOf(
                ShoppingItemDto(
                    id = EXIST_ITEM_ID,
                    name = EXIST_ITEM_NAME,
                    checked = EXIST_ITEM_CHECKED
                )
            ),
            result
        )
    }


    @Test
    fun getShoppingListObservable_InitWithEmptyData_ReturnEmptyList() {
        setEmptyData()
        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)

        shoppingListLocalDataSource.getShoppingListObservable()
            .test()
            .assertNoErrors()
            .assertValue(
                listOf()
            )
    }


    @Test
    fun getShoppingListObservable_InitWithExtraData_ReturnListOfShoppingItemDto() {
        setAbnormalDataWithExtraField()
        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)

        shoppingListLocalDataSource.getShoppingListObservable()
            .concatWith(Observable.never())
            .test()
            .assertNoErrors()
            .assertValue(
                listOf(
                    ShoppingItemDto(
                        id = EXIST_ITEM_ID,
                        name = EXIST_ITEM_NAME,
                        checked = EXIST_ITEM_CHECKED
                    )
                )
            )
    }

    @Test
    fun getShoppingListObservable_InitWithoutSomeData_ReturnListOfShoppingItemDtoWithDefaultValue() {
        setAbnormalDataWithoutSomeField()

        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)
        shoppingListLocalDataSource.getShoppingListObservable()
            .concatWith(Observable.never())
            .test()
            .assertNoErrors()
            .assertValue(
                listOf(
                    ShoppingItemDto(
                        id = EXIST_ITEM_ID,
                        name = "",
                        checked = EXIST_ITEM_CHECKED
                    )
                )
            )
    }

    @Test
    fun saveShoppingItem_VerifyUpdateStream() {
        setEmptyData()
        val newItem = ShoppingItemDto(
            id = NEW_ITEM_ID,
            name = NEW_ITEM_NAME,
            checked = NEW_ITEM_CHECKED
        )

        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)
        shoppingListLocalDataSource.getShoppingListObservable()
            .test()
            .awaitCount(2)
        shoppingListLocalDataSource.saveShoppingItem(newItem)

        val key = NEW_ITEM_ID
        val value =
            "{\"id\":\"${NEW_ITEM_ID}\",\"name\":\"${NEW_ITEM_NAME}\",\"checked\":${NEW_ITEM_CHECKED}}"

        verify { mockSharedPreferencesEditor.putString(key, value) }
    }

    @Test
    fun removeShoppingItem_VerifyUpdateStream() {
        setEmptyData()
        shoppingListLocalDataSource = ShoppingListLocalDataSource(mockContext)

        shoppingListLocalDataSource.getShoppingListObservable()
            .test()
            .awaitCount(2)
        shoppingListLocalDataSource.removeShoppingItem(EXIST_ITEM_ID)
        verify { mockSharedPreferencesEditor.remove(EXIST_ITEM_ID) }
    }

    companion object {
        private const val EXIST_ITEM_ID = "EXIST_ITEM_ID"
        private const val EXIST_ITEM_NAME = "EXIST_ITEM_NAME"
        private const val EXIST_ITEM_CHECKED = true

        private const val NEW_ITEM_ID = "NEW_ITEM_ID"
        private const val NEW_ITEM_NAME = "NEW_ITEM_NAME"
        private const val NEW_ITEM_CHECKED = false
    }
}