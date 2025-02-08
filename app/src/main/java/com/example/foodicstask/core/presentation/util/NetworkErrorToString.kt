package com.example.foodicstask.core.presentation.util

import com.example.foodicstask.R
import com.example.foodicstask.core.domain.util.NetworkError

fun NetworkError.toTextResource(): TextResource {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> TextResource.StringResourceId(R.string.error_request_timeout)
        NetworkError.TOO_MANY_REQUESTS -> TextResource.StringResourceId(R.string.error_too_many_requests)
        NetworkError.NO_INTERNET -> TextResource.StringResourceId(R.string.error_no_internet)
        NetworkError.SERVER_ERROR -> TextResource.StringResourceId(R.string.error_unknown)
        NetworkError.SERIALIZATION -> TextResource.StringResourceId(R.string.error_serialization)
        NetworkError.UNKNOWN -> TextResource.StringResourceId(R.string.error_unknown)
    }
}
