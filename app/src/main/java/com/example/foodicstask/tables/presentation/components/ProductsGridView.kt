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
import com.example.foodicstask.tables.presentation.models.ProductUi
import com.example.foodicstask.ui.theme.CoolGray

@Composable
fun ProductsGridView(
    modifier: Modifier = Modifier,
    products: List<ProductUi>,
    searchResult: List<ProductUi> = emptyList(),
    onClick: (ProductUi) -> Unit
) {
    val itemsToDisplay = if (searchResult.isNotEmpty()) {
        searchResult
    } else {
        products
    }

    if (itemsToDisplay.isEmpty())
        return

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
            items(itemsToDisplay.size) { index ->
                ProductCard(
                    product = itemsToDisplay[index],
                    onClick = onClick
                )
            }
        }
    )
}
