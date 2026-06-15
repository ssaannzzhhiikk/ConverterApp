package com.sanzh.currency_library.domain.model

data class Currency(
    val code: String,
    val name: String,
    val rate: Double,
    val change24h: Double
)