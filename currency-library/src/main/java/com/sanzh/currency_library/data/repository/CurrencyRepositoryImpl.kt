package com.sanzh.currency_library.data.repository

import com.sanzh.currency_library.data.mapper.CurrencyMapper
import com.sanzh.currency_library.data.remote.CurrencyApi
import com.sanzh.currency_library.domain.repository.CurrencyRepository
import com.sanzh.currency_library.domain.model.Currency

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val mapper: CurrencyMapper
) : CurrencyRepository {

    override suspend fun getCurrencies(base: String): List<Currency> {
        val response = api.getRates(base)
        return mapper.mapToDomain(response)
    }
}