package com.example.foodicstask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.foodicstask.tables.presentation.tables_screen.TablesScreenRoot
import com.example.foodicstask.ui.theme.FoodicsTaskTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodicsTaskTheme {
                TablesScreenRoot()
            }
        }
    }
}
