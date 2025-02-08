package com.example.foodicstask.tables.presentation.tables_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodicstask.R
import com.example.foodicstask.ui.theme.Purple

@Composable
fun OrderSummaryView(
    modifier: Modifier = Modifier,
    totalPrice: Double = 1234.00,
    priceUnit: String = "SAR",
    orderedProducts: Int = 3
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 4.dp)
            .background(Purple, shape = RoundedCornerShape(10)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = orderedProducts.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = stringResource(R.string.view_order),
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$priceUnit $totalPrice",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
