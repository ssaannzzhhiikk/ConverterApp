package com.sanzh.currency_library.data.remote

import com.sanzh.currency_library.data.remote.dto.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    // ExchangeRate-API free endpoint: https://open.er-api.com/v6/latest/{base}
    @GET("v6/latest/{base}")
    suspend fun getRates(@Path("base") base: String): ExchangeRateResponse
}