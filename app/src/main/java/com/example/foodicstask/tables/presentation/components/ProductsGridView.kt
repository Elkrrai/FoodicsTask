package com.example.foodicstask.tables.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodicstask.core.presentation.util.WindowInfo
import com.example.foodicstask.core.presentation.util.rememberWindowInfo
import com.example.foodicstask.tables.domain.entities.Category
import com.example.foodicstask.tables.domain.entities.Product
import com.example.foodicstask.tables.presentation.models.CategoryUi
import com.example.foodicstask.tables.presentation.models.ProductUi
import com.example.foodicstask.ui.theme.CoolGray

@Composable
fun ProductsGridView(
    modifier: Modifier = Modifier,
    products: List<ProductUi>
) {
    val windowInfo = rememberWindowInfo()
    val columns = when (windowInfo.screenWidthInfo) {
        is WindowInfo.Type.Compact -> 3
        is WindowInfo.Type.Medium -> 4
        is WindowInfo.Type.Expanded -> 4
    }

    LazyVerticalGrid(
        modifier = modifier
            .background(CoolGray)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        columns = GridCells.Fixed(columns),
        content = {
            items(20) { index ->
                val dummyProduct = ProductUi(
                    id = index + 1,
                    name = "Product ${index + 1}",
                    price = (10.0 + index).toDouble(),
                    image = "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg",
                    description = "This is product ${index + 1}",
                    ordered = 12,
                    category = CategoryUi(
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
