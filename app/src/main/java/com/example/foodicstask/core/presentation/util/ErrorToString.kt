package com.example.foodicstask.core.presentation.util

import android.content.Context
import com.example.foodicstask.R
import com.example.foodicstask.core.domain.util.Error

fun Error.getMessage(context: Context): String {
    val resId = when (this) {
        Error.NetworkError.NoInternet -> R.string.error_no_internet
        Error.NetworkError.RequestTimeout -> R.string.error_request_timeout
        Error.NetworkError.Serialization -> R.string.error_serialization
        Error.NetworkError.ServerError -> R.string.error_unknown
        Error.NetworkError.TooManyRequests -> R.string.error_too_many_requests
        Error.NetworkError.Unknown -> R.string.error_unknown
        Error.NoSearchResult -> R.string.no_search_result
    }
    return context.getString(resId)
}
