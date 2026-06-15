package com.sanzh.currency_library.domain.repository

import com.sanzh.currency_library.domain.model.Currency

interface CurrencyRepository {
    suspend fun getCurrencies(base: String): List<Currency>
}