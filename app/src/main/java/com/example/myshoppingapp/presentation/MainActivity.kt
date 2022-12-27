package com.example.myshoppingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import com.example.myshoppingapp.presentation.viewcomponent.BottomSheet
import com.example.myshoppingapp.presentation.viewcomponent.MenuButton
import com.example.myshoppingapp.presentation.viewcomponent.ShoppingList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(contentAlignment = Alignment.BottomEnd) {
                Column {
                    ShoppingList()
                    MenuButton()
                }
                BottomSheet()
            }
        }
    }
}