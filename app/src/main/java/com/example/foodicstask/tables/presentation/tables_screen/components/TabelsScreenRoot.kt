package com.example.foodicstask.tables.presentation.tables_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodicstask.tables.presentation.tables_screen.TablesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TablesScreenRoot(
    viewModel: TablesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    TablesScreen(
        state = state.value,
        modifier = modifier,
        onAction = viewModel::onAction
    )
}
