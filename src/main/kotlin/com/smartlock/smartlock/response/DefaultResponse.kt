package com.smartlock.smartlock.response

data class DefaultResponse<T>(
        var status: Boolean = false,
        var message: String? = null,
        var data: ArrayList<T>? = null
)