package com.example.myshoppingapp.data.shoppingitem

import com.example.myshoppingapp.domain.ShoppingItem
import io.mockk.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ShoppingListRepositoryImplTest {

    private lateinit var shoppingListRepositoryImpl: ShoppingListRepositoryImpl

    private val mockShoppingItem: ShoppingItem = mockk()
    private val mockShoppingItemDto: ShoppingItemDto = mockk()
    private val mockShoppingListLocalDataSource: ShoppingListLocalDataSource = mockk()
    private val mockShoppingItemModelMapper: ShoppingItemModelMapper = mockk()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

        every { mockShoppingItemModelMapper.toDomainLayer(mockShoppingItemDto) } returns mockShoppingItem
        every { mockShoppingItemModelMapper.fromDomainLayer(mockShoppingItem) } returns mockShoppingItemDto

        shoppingListRepositoryImpl =
            ShoppingListRepositoryImpl(mockShoppingListLocalDataSource, mockShoppingItemModelMapper)
    }

    @Test
    fun getShoppingListObservable_OnSubscribeObservable_ReturnListOfShoppingItem() {
        every { mockShoppingListLocalDataSource.loadShoppingList() } returns listOf(
            mockShoppingItemDto
        )
        every { mockShoppingListLocalDataSource.getShoppingListObservable() } returns Observable.just(
            listOf(mockShoppingItemDto)
        )

        shoppingListRepositoryImpl.getShoppingListObservable()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(listOf(mockShoppingItem))

        verify { mockShoppingListLocalDataSource.getShoppingListObservable() }
        verify { mockShoppingItemModelMapper.toDomainLayer(mockShoppingItemDto) }
    }

    @Test
    fun getShoppingListObservable_OnErrorObservable_ReturnThrowable() {
        every { mockShoppingListLocalDataSource.loadShoppingList() } returns listOf(
            mockShoppingItemDto
        )
        every { mockShoppingListLocalDataSource.getShoppingListObservable() } returns Observable.error(
            Throwable("Test")
        )

        shoppingListRepositoryImpl.getShoppingListObservable()
            .test()
            .assertError(Throwable::class.java)

        verify { mockShoppingListLocalDataSource.getShoppingListObservable() }
    }

    @Test
    fun getShoppingList_ExistShoppingItem_ReturnListOfShoppingItem() {
        every { mockShoppingListLocalDataSource.loadShoppingList() } returns listOf(
            mockShoppingItemDto
        )
        val result = shoppingListRepositoryImpl.getShoppingList()

        assertEquals(
            listOf(mockShoppingItem),
            result
        )
        verify { mockShoppingListLocalDataSource.loadShoppingList() }
        verify { mockShoppingItemModelMapper.toDomainLayer(mockShoppingItemDto) }
    }

    @Test
    fun getShoppingList_NotExistShoppingItem_ReturnEmptyList() {
        every { mockShoppingListLocalDataSource.loadShoppingList() } returns listOf()

        val result = shoppingListRepositoryImpl.getShoppingList()

        assertEquals(
            listOf<ShoppingItem>(),
            result
        )
        verify { mockShoppingListLocalDataSource.loadShoppingList() }
    }

    @Test
    fun saveShoppingItem_VerifyMappingAndSaving() {
        every { mockShoppingListLocalDataSource.saveShoppingItem(mockShoppingItemDto) } just runs

        shoppingListRepositoryImpl.saveShoppingItem(mockShoppingItem)

        verify { mockShoppingListLocalDataSource.saveShoppingItem(mockShoppingItemDto) }
        verify { mockShoppingItemModelMapper.fromDomainLayer(mockShoppingItem) }
    }

    @Test
    fun removeShoppingItem_VerifyRemovingWithSameId() {
        val testId = "TestId"
        every { mockShoppingListLocalDataSource.removeShoppingItem(testId) } just runs
        shoppingListRepositoryImpl.removeShoppingItem(testId)

        verify { mockShoppingListLocalDataSource.removeShoppingItem(testId) }
    }

    @Test
    fun updateShoppingItem_VerifyMappingAndSaving() {
        every { mockShoppingListLocalDataSource.saveShoppingItem(mockShoppingItemDto) } just runs

        shoppingListRepositoryImpl.updateShoppingItem(mockShoppingItem)

        verify { mockShoppingListLocalDataSource.saveShoppingItem(mockShoppingItemDto) }
        verify { mockShoppingItemModelMapper.fromDomainLayer(mockShoppingItem) }
    }
}