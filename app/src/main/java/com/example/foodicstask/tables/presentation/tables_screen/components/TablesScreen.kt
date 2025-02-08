package com.example.foodicstask.tables.presentation.tables_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import com.example.foodicstask.tables.presentation.tables_screen.TablesAction
import com.example.foodicstask.tables.presentation.tables_screen.TablesState
import com.example.foodicstask.ui.theme.PurpleGrey40

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

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            modifier = Modifier
                .background(PurpleGrey40)
                .padding(horizontal = 4.dp),
            columns = GridCells.Fixed(3),
            content = {
                items(20) { index ->
                    val dummyProduct = Product(
                        id = index + 1,
                        name = "Product ${index + 1}",
                        price = (10.0 + index).toDouble(),
                        image = "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg",
                        description = "This is product ${index + 1}",
                        ordered = 0,
                        category = Category(
                            id = (index % 3) + 1,
                            name = when (index % 3) {
                                0 -> "Breakfast"
                                1 -> "Lunch"
                                else -> "Dinner"
                            }
                        )
                    )

                    ProductCard(product = dummyProduct)
                }
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
