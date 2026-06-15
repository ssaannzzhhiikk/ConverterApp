package com.sanzh.converterapp.presentation.event

sealed class CurrencyEvent {
    data class LoadCurrencies(val base: String) : CurrencyEvent()
    data class ConvertMoney(
        val amount: Double,
        val fromCode: String,
        val toCode: String
    ) : CurrencyEvent()
    object ClearConversion : CurrencyEvent()
}