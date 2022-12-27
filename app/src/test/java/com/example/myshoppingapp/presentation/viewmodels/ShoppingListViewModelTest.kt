package com.example.myshoppingapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.domain.usecases.GetShoppingListObservableUseCase
import com.example.myshoppingapp.domain.usecases.RemoveShoppingItemUseCase
import com.example.myshoppingapp.domain.usecases.UpdateShoppingItemUseCase
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class ShoppingListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingListViewModel: ShoppingListViewModel

    private val mockShoppingItem: ShoppingItem = mockk()
    private val mockCheckedShoppingItem: ShoppingItem = mockk()
    private val mockUncheckedShoppingItem: ShoppingItem = mockk()
    private val removeShoppingItemUseCase: RemoveShoppingItemUseCase = mockk()
    private val getShoppingListObservableUseCase: GetShoppingListObservableUseCase = mockk()
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase = mockk()

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        every { mockShoppingItem.checked } returns false
        every { mockCheckedShoppingItem.checked } returns true
        every { mockCheckedShoppingItem.id } returns CHECK_ITEM_ID
        every { mockUncheckedShoppingItem.checked } returns false
        every { mockUncheckedShoppingItem.checked } returns false
    }

    @Test
    fun getShoppingItems_OnSuccessLoadDataWithListOfShoppingItem_ReturnListOfShoppingItem() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockShoppingItem
            )
        )

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            listOf(mockShoppingItem),
            shoppingListViewModel.shoppingItems.value
        )
    }

    @Test
    fun getShoppingItems_OnError_ReturnEmptyList() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.error(Throwable())

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            listOf<ShoppingItem>(),
            shoppingListViewModel.shoppingItems.value
        )
    }

    @Test
    fun getShoppingItems_EmptyList_ReturnEmptyList() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(listOf())

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            listOf<ShoppingItem>(),
            shoppingListViewModel.shoppingItems.value
        )
    }


    @Test
    fun getCheckedItems_OnSuccessLoadDataWithListOfUncheckedShoppingItem_ReturnFalse() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockUncheckedShoppingItem
            )
        )

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            false,
            shoppingListViewModel.existCheckedItem.value
        )
    }

    @Test
    fun getCheckedItems_OnSuccessLoadDataWithListOfUncheckedShoppingItemAndCheckedShoppingItem_ReturnTrue() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockUncheckedShoppingItem,
                mockCheckedShoppingItem
            )
        )

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            true,
            shoppingListViewModel.existCheckedItem.value
        )
    }

    @Test
    fun getCheckedItems_OnSuccessLoadDataWithListOfCheckedShoppingItem_ReturnTrue() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockCheckedShoppingItem
            )
        )

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            true,
            shoppingListViewModel.existCheckedItem.value
        )
    }


    @Test
    fun getCheckedItems_OnErrorLoadData_UpdateAny() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.error(Throwable())

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            false,
            shoppingListViewModel.existCheckedItem.value
        )
    }


    @Test
    fun getCheckedItem_EmptyList_ReturnFalse() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(listOf())

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        assertEquals(
            false,
            shoppingListViewModel.existCheckedItem.value
        )
    }

    @Test
    fun toggleChecked_InvokeUpdateWithToggledItem() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockUncheckedShoppingItem
            )
        )
        every { mockUncheckedShoppingItem.toggleChecked() } returns mockCheckedShoppingItem
        every { updateShoppingItemUseCase.invoke(mockCheckedShoppingItem) } just runs

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        shoppingListViewModel.toggleChecked(mockUncheckedShoppingItem)

        verify { updateShoppingItemUseCase.invoke(mockCheckedShoppingItem) }
    }

    @Test
    fun removeCheckedItems_InvokeUpdateWithToggledItem() {
        every { getShoppingListObservableUseCase.invoke() } returns Observable.just(
            listOf(
                mockCheckedShoppingItem,
                mockUncheckedShoppingItem
            )
        )
        every { removeShoppingItemUseCase.invoke(listOf(CHECK_ITEM_ID)) } just runs

        shoppingListViewModel = ShoppingListViewModel(
            removeShoppingItemUseCase,
            getShoppingListObservableUseCase,
            updateShoppingItemUseCase
        )

        shoppingListViewModel.removeCheckedItems()

        verify { removeShoppingItemUseCase.invoke(listOf(CHECK_ITEM_ID)) }
    }

    companion object {
        const val CHECK_ITEM_ID = "CHECK_ITEM_ID"
    }
}