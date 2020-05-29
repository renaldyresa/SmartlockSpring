package com.smartlock.smartlock.model

data class AuthenticationRequest(
        var username: String,
        var password: String
)