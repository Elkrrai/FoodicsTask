package com.example.foodicstask.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class WindowInfo(
    val screenWidthInfo: Type,
    val screenHeightInfo: Type,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class Type {
        object Compact : Type()
        object Medium : Type()
        object Expanded : Type()
    }
}

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.Type.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.Type.Medium
            else -> WindowInfo.Type.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> WindowInfo.Type.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.Type.Medium
            else -> WindowInfo.Type.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}
