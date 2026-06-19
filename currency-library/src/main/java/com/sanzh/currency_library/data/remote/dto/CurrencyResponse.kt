package com.sanzh.currency_library.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("result") val result: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("rates") val conversionRates: Map<String, Double>
)

// We simulate 24h change since free tier doesn't provide it directly.
// In a real app you'd store yesterday's rates in local DB and compute the diff.
data class CurrencyDto(
    val code: String,
    val rate: Double,
    val change24h: Double
)