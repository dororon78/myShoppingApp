package com.example.myshoppingapp.presentation.viewcomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myshoppingapp.domain.ShoppingItem
import com.example.myshoppingapp.presentation.viewmodels.ItemAddFormViewModel


@Composable
fun ItemAddForm(
    itemAddFormViewModel: ItemAddFormViewModel = hiltViewModel()
) {
    val title by itemAddFormViewModel.title.observeAsState("")
    val comment by itemAddFormViewModel.comment.observeAsState("")
    val advancedSettingVisibility by itemAddFormViewModel.advancedSettingVisibility.observeAsState(
        false
    )
    val amount by itemAddFormViewModel.amount.observeAsState(0)
    val unit by itemAddFormViewModel.unit.observeAsState("")

    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            "추가",
            modifier = Modifier
                .padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 15.sp
            )
        )

        TextField(
            title,
            singleLine = true,
            onValueChange = {
                itemAddFormViewModel.updateTitle(it)
            },
            label = { Text("상품명") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("textField_item_name")
        )

        if (comment.isNotEmpty()) {
            Text(comment)
        }

        if (advancedSettingVisibility) {
            Text(
                "고급설정",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                style = TextStyle(
                    fontSize = 12.sp
                ),
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                TextField(
                    if (amount > 0) amount.toString() else "",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { itemAddFormViewModel.updateAmount(it) },
                    label = { Text("수량") },
                    modifier = Modifier.weight(1f, true)
                )
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    unit,
                    singleLine = true,
                    onValueChange = { itemAddFormViewModel.updateUnit(it) },
                    label = { Text("단위") },
                    modifier = Modifier
                        .width(100.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(onClick = { itemAddFormViewModel.clearForm() }) {
                Text("초기화")
            }
            TextButton(onClick = {
                itemAddFormViewModel.toggleAdvancedSettingVisibility(
                    advancedSettingVisibility
                )
            }) {
                Text(if (advancedSettingVisibility) "간편설정" else "고급설정")
            }
            TextButton(
                modifier = Modifier.testTag("new_item_save_button"),
                onClick = {
                    itemAddFormViewModel.createItem(
                        ShoppingItem(
                            name = title,
                            amount = amount,
                            unit = unit,
                        )
                    )
                },
            ) {
                Text("저장")
            }
        }
    }
}
