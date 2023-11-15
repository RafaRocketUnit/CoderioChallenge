package com.rafarocket.coderiochallenge.domain.model

data class Error(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)