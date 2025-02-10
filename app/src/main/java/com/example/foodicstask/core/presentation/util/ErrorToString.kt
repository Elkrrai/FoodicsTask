package com.example.foodicstask.core.presentation.util

import android.content.Context
import com.example.foodicstask.R
import com.example.foodicstask.core.domain.util.DataError

fun DataError.getMessage(context: Context): String {
    val resId = when (this) {
        DataError.NetworkError.NoInternet -> R.string.error_no_internet
        DataError.NetworkError.RequestTimeout -> R.string.error_request_timeout
        DataError.NetworkError.Serialization -> R.string.error_serialization
        DataError.NetworkError.ServerError -> R.string.error_unknown
        DataError.NetworkError.TooManyRequests -> R.string.error_too_many_requests
        DataError.NetworkError.Unknown -> R.string.error_unknown
        DataError.NoSearchResult -> R.string.no_search_result
        DataError.LocalError -> R.string.local_data_error
    }
    return context.getString(resId)
}
