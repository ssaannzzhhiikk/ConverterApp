package com.sanzh.currency_library.domain.usecase

import com.sanzh.currency_library.domain.model.Currency

class ConvertCurrencyUseCase {
    operator fun invoke(amount: Double, from: Currency, to: Currency): Double {
        if (from.rate == 0.0) return 0.0
        val amountInBase = amount / from.rate
        return amountInBase * to.rate
    }
}