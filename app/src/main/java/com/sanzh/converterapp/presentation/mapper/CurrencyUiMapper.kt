package com.sanzh.converterapp.presentation.mapper

import com.sanzh.currency_library.domain.model.Currency
import com.sanzh.converterapp.presentation.model.CurrencyUiModel

object CurrencyUiMapper {
    fun toUiModel(domain: Currency): CurrencyUiModel {
        val changePrefix = if (domain.change24h >= 0) "+" else ""
        return CurrencyUiModel(
            code = domain.code,
            name = domain.name,
            rate = String.format("%.4f", domain.rate),
            change24h = "$changePrefix${String.format("%.2f", domain.change24h)}%",
            isPositiveChange = domain.change24h >= 0
        )
    }

    fun toUiModelList(currencies: List<Currency>): List<CurrencyUiModel> {
        return currencies.map { toUiModel(it) }
    }
}