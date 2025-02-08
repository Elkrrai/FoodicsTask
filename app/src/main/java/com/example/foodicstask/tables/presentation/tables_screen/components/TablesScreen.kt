package com.example.foodicstask.tables.presentation.tables_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodicstask.tables.presentation.tables_screen.TablesAction
import com.example.foodicstask.tables.presentation.tables_screen.TablesState

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
            categories = getDummyCategories(),
            selectedTabIndex = state.selectedCategoryIndex,
            onTabSelected = {
                onAction(TablesAction.OnCategorySelected(it))
            }
        )

        ProductsGridView(products = state.products)
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

fun getDummyCategories(): List<String> {
    return listOf("Breakfast", "Lunch", "Dinner", "Sweets", "Ice", "Fruit", "Drink")
}
