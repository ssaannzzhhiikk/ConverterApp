package com.sanzh.currency_library.domain.interactor

import com.sanzh.currency_library.domain.model.Currency
import com.sanzh.currency_library.domain.usecase.ConvertCurrencyUseCase
import com.sanzh.currency_library.domain.usecase.GetCurrenciesUseCase

class CurrencyInteractor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) {
    suspend fun loadCurrencies(base: String): List<Currency> {
        return getCurrenciesUseCase(base)
    }

    fun convert(amount: Double, from: Currency, to: Currency): Double {
        return convertCurrencyUseCase(amount, from, to)
    }
}