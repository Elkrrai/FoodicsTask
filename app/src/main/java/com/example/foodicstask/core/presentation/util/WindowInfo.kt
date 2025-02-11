package com.example.foodicstask.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class representing information about the current window's dimensions and type.
 *
 * This class provides details about the screen's width and height, as well as
 * categorized information about whether the screen is considered compact, medium, or expanded
 * in both width and height dimensions.
 *
 * @property screenWidthInfo The [Type] of the screen's width (Compact, Medium, or Expanded).
 * @property screenHeightInfo The [Type] of the screen's height (Compact, Medium, or Expanded).
 * @property screenWidth The actual width of the screen in [Dp].
 * @property screenHeight The actual height of the screen in [Dp].
 */
data class WindowInfo(
    val screenWidthInfo: Type,
    val screenHeightInfo: Type,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    /**
     * Represents the type of a screen dimension (width or height).
     *
     * This class categorizes screen dimensions into three types: Compact, Medium, and Expanded.
     */
    sealed class Type {
        /**
         * Represents a compact screen dimension.
         */
        object Compact : Type()

        /**
         * Represents a medium screen dimension.
         */
        object Medium : Type()

        /**
         * Represents an expanded screen dimension.
         */
        object Expanded : Type()
    }
}

/**
 * Remembers and provides [WindowInfo] about the current window's dimensions and type.
 *
 * This composable function calculates and returns a [WindowInfo] object based on the
 * current screen configuration.
 *
 * The [WindowInfo] object is remembered across recompositions, so the calculation
 * is only performed when the configuration changes.
 *
 * @return A [WindowInfo] object containing the current screen's width and height information.
 */
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
