package com.sanzh.converterapp.presentation.state

import com.sanzh.converterapp.presentation.model.CurrencyUiModel

data class CurrencyUiState(
    val isLoading: Boolean = false,
    val currencies: List<CurrencyUiModel> = emptyList(),
    val error: String? = null,
    val convertedAmount: String? = null,
    val baseCurrency: String = "USD"
)