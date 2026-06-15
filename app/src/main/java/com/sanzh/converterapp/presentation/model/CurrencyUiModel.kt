package com.sanzh.converterapp.presentation.model

data class CurrencyUiModel(
    val code: String,
    val name: String,
    val rate: String,
    val change24h: String,
    val isPositiveChange: Boolean
)