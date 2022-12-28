package com.example.myshoppingapp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myshoppingapp.presentation.MainActivity
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// FIXME: 한 라인씩 늦게 동작함.
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun verify_if_all_views_exists() {
        composeTestRule.onNodeWithTag("add_item_button").assertExists()
        composeTestRule.onNodeWithTag("remove_item_button").assertExists()
        composeTestRule.onNodeWithTag("done_shopping_button").assertExists()
    }

    private fun add_item(name: String) {
        composeTestRule.onNodeWithTag("add_item_button").performClick()
        composeTestRule.onNodeWithTag("textField_item_name").performTextInput(name)
        composeTestRule.onNodeWithTag("new_item_save_button").performClick()
        composeTestRule.onNodeWithTag("bottom_sheet_scrim").performClick()
    }

    @Test
    fun check_item() {
        verify_if_all_views_exists()

        val itemName = "TestItemForCheck"
        add_item(itemName)
        composeTestRule.onNodeWithText(itemName).assertExists()
        composeTestRule.onNodeWithText(itemName).assertTextColor(Color.Black)

        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithText(itemName).assertTextColor(Color.LightGray)
    }

    @Test
    fun uncheck_item() {
        verify_if_all_views_exists()

        val itemName = "TestItemForUncheck"
        add_item(itemName)
        composeTestRule.onNodeWithText(itemName).assertExists()
        composeTestRule.onNodeWithText(itemName).assertTextColor(Color.Black)

        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithText(itemName).assertTextColor(Color.LightGray)

        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithText(itemName).assertTextColor(Color.Black)
    }

    @Test
    fun verify_add_item() {
        verify_if_all_views_exists()

        val itemName = "TestItem"
        add_item(itemName)
        composeTestRule.onNodeWithText(itemName).assertExists()
    }

    @Test
    fun remove_item() {
        verify_if_all_views_exists()

        val itemName = "TestItemForRemove"
        add_item(itemName)
        composeTestRule.onNodeWithText(itemName).assertExists()

        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithTag("remove_item_button").performClick()
        composeTestRule.onNodeWithText(itemName).assertDoesNotExist()
    }

    @Test
    fun done_shopping() {
        verify_if_all_views_exists()

        val itemName = "TestItemForDoneShopping"
        add_item(itemName)

        composeTestRule.onNodeWithText(itemName).assertExists()

        composeTestRule.onNodeWithText(itemName).performClick()
        composeTestRule.onNodeWithTag("done_shopping_button").performClick()

        composeTestRule.onNodeWithTag("external_app_launcher").assertExists()

        composeTestRule.onNodeWithTag("bottom_sheet_scrim").performClick()
        composeTestRule.onNodeWithText(itemName).assertDoesNotExist()
    }
}


// https://stackoverflow.com/questions/70988755/compose-ui-testing-how-do-i-assert-a-text-color
fun SemanticsNodeInteraction.assertTextColor(
    color: Color
): SemanticsNodeInteraction = assert(isOfColor(color))

private fun isOfColor(color: Color): SemanticsMatcher = SemanticsMatcher(
    "${SemanticsProperties.Text.name} is of color '$color'"
) {
    val textLayoutResults = mutableListOf<TextLayoutResult>()
    it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
        ?.action
        ?.invoke(textLayoutResults)
    return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
        false
    } else {
        textLayoutResults.first().layoutInput.style.color == color
    }
}