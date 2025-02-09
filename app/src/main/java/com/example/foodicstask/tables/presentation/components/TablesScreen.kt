package com.example.foodicstask.tables.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodicstask.tables.presentation.TablesAction
import com.example.foodicstask.tables.presentation.TablesState

@Composable
fun TablesScreen(
    state: TablesState,
    modifier: Modifier = Modifier,
    onAction: (TablesAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarView()

        Spacer(modifier = Modifier.height(4.dp))

        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(TablesAction.OnSearchQuerySubmit(it))
            },
            onImeSearch = {
                onAction(TablesAction.OnSearchQuerySubmit(state.searchQuery))
                keyboardController?.hide()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        CategoriesTabLayout(
            categories = state.categories,
            selectedTabIndex = state.selectedCategoryIndex,
            onTabSelected = { index, id ->
                onAction(TablesAction.OnCategorySelected(index, id))
            }
        )

        ProductsGridView(
            products = state.products,
            modifier = Modifier
                .weight(1f)
        )

        OrderSummaryView(
            modifier = Modifier
                .background(Color.White),
            totalPrice = state.totalPrice,
            orderedProducts = state.orderedProducts
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
