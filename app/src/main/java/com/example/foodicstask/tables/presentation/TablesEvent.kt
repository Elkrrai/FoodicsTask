package com.example.foodicstask.tables.presentation
import com.example.foodicstask.core.domain.util.DataError

sealed interface TablesEvent {
    data class ShowError(val error: DataError): TablesEvent
}
