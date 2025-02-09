package com.example.foodicstask.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface TextResource {
    data class DynamicString(val value: String) : TextResource
    class StringResourceId(
        val id: Int,
        val args: Array<Any> = arrayOf()
    ) : TextResource

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id, args)
        }
    }
}