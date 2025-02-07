package com.example.foodicstask.tables.presentation.tables_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TablesScreen(
    state: TablesState,
    modifier: Modifier = Modifier,
    onAction: (TablesAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(TablesAction.OnSearchQuerySubmit(it))
            },
            onImeSearch = {
                onAction(TablesAction.OnSearchQuerySubmit(state.searchQuery))
                keyboardController?.hide()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TablesScreenPreview() {
    TablesScreen(
        state = TablesState(),
        onAction = {}
    )
}
