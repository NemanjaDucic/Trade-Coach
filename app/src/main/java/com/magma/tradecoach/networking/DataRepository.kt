package com.magma.tradecoach.networking

import com.magma.tradecoach.model.MarketCoinModel
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object DataRepository {
        var data : List<MarketCoinModel> ?= emptyList()
}
