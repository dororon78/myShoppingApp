package com.example.myshoppingapp.presentation.viewcomponent

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.presentation.viewmodels.ShoppingListViewModel

@Composable
fun ColumnScope.ShoppingList(
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
) {
    val list: List<ShoppingItem> by shoppingListViewModel.shoppingItems.observeAsState(listOf())
    LazyColumn(
        modifier = Modifier
            .weight(1f, true)
            .testTag("ShoppingList"),
        content = { items(list) { item -> ShoppingItemView(item) } }
    )
}

@Composable
fun ShoppingItemView(
    item: ShoppingItem,
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel(),
) {
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        style = TextStyle(
            fontSize = 20.sp,
            color = if (item.checked) Color.LightGray else Color.Black
            //            textDecoration = if (item.bought) TextDecoration.LineThrough else TextDecoration.None, // 버그 있음.
        ),
        text = AnnotatedString(item.toString()),
        onClick = { shoppingListViewModel.toggleChecked(item) }
    )
}