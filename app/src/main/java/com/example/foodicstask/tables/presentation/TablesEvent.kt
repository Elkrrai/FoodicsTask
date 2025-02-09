package com.example.foodicstask.tables.presentation

import com.example.foodicstask.core.domain.util.NetworkError

sealed interface TablesEvent {
    data class Error(val error: NetworkError): TablesEvent
}
