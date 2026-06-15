package com.sanzh.currency_library.domain.usecase

import com.sanzh.currency_library.domain.model.Currency
import com.sanzh.currency_library.domain.repository.CurrencyRepository

class GetCurrenciesUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(base: String): List<Currency> {
        return repository.getCurrencies(base)
    }
}