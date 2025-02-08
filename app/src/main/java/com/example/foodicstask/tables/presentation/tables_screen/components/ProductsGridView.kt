package com.example.foodicstask.tables.presentation.tables_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import com.example.foodicstask.ui.theme.PurpleGrey40

@Composable
fun ProductsGridView(
    modifier: Modifier = Modifier,
    products: List<Product>
) {
    LazyVerticalGrid(
        modifier = modifier
            .background(PurpleGrey40)
            .padding(horizontal = 8.dp),
        columns = GridCells.Fixed(3),
        content = {
            items(20) { index ->
                val dummyProduct = Product(
                    id = index + 1,
                    name = "Product ${index + 1}",
                    price = (10.0 + index).toDouble(),
                    image = "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg",
                    description = "This is product ${index + 1}",
                    ordered = 5,
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
