package com.example.foodicstask.tables.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodicstask.tables.presentation.models.CategoryUi
import com.example.foodicstask.ui.theme.DarkBlue

@Composable
fun CategoriesTabLayout(
    categories: List<CategoryUi>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories.size) { index ->
            val isSelected = selectedTabIndex == index
            CategoryTabItem(
                title = categories[index].name,
                isSelected = isSelected,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun CategoryTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var textWidthPx by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
            .widthIn(min = 80.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color.Gray
            ),
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    textWidthPx = layoutCoordinates.size.width.toFloat()
                }
        )
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .width(with(density) { textWidthPx.toDp() })
                    .background(DarkBlue, shape = RoundedCornerShape(1.dp))
            )
        }
    }
}
