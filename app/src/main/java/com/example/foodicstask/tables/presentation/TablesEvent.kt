package com.example.foodicstask.tables.presentation
import com.example.foodicstask.core.domain.util.Error

sealed interface TablesEvent {
    data class ShowError(val error: Error): TablesEvent
}
