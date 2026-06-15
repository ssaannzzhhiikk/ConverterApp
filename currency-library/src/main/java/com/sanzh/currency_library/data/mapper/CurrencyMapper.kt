package com.sanzh.currency_library.data.mapper

import com.sanzh.currency_library.data.remote.dto.ExchangeRateResponse
import com.sanzh.currency_library.domain.model.Currency
import kotlin.random.Random

object CurrencyMapper {

    private val CURRENCY_NAMES = mapOf(
        "USD" to "US Dollar",
        "EUR" to "Euro",
        "GBP" to "British Pound",
        "JPY" to "Japanese Yen",
        "CHF" to "Swiss Franc",
        "CAD" to "Canadian Dollar",
        "AUD" to "Australian Dollar",
        "CNY" to "Chinese Yuan",
        "KZT" to "Kazakhstani Tenge",
        "RUB" to "Russian Ruble",
        "TRY" to "Turkish Lira",
        "INR" to "Indian Rupee",
        "BRL" to "Brazilian Real",
        "MXN" to "Mexican Peso",
        "SEK" to "Swedish Krona"
    )

    fun mapToDomain(response: ExchangeRateResponse): List<Currency> {
        return response.conversionRates
            .filter { CURRENCY_NAMES.containsKey(it.key) }
            .map { (code, rate) ->
                Currency(
                    code = code,
                    name = CURRENCY_NAMES[code] ?: code,
                    rate = rate,
                    change24h = generateSimulatedChange()
                )
            }
            .sortedBy { it.code }
    }

    // Simulates 24h change since the free API doesn't provide historical data.
    // Replace with real historical diff when using a paid plan or local DB cache.
    private fun generateSimulatedChange(): Double {
        return Random.nextDouble(-5.0, 5.0).let {
            Math.round(it * 100.0) / 100.0
        }
    }
}