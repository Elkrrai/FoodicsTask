package com.example.foodicstask.tables.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodicstask.tables.presentation.TablesViewModel
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
