package com.magma.tradecoach.networking

import com.magma.tradecoach.model.MarketCoinModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinMarketApi {
    val string: String
    @GET("coins/markets")
    suspend fun coinListing(
        @Header("x_cg_demo_api_key") apiKey: String,
        @Query("vs_currency") currency: String
    ): Response<List<MarketCoinModel>>
}