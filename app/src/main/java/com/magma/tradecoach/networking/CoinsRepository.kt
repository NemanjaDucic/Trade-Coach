package com.magma.tradecoach.networking

import com.magma.tradecoach.model.MarketCoinModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinsRepository @Inject constructor(val api: CoinMarketApi) {
    private val apikey = "CG-ZxGvPrXmY2Yc6WeFh4aAK2cP"
    private val currency = "usd"
    private var cachedData: List<MarketCoinModel>? = null
    private var isDataFetched = false

    suspend fun getResults(): Result<List<MarketCoinModel>> {
        if (!isDataFetched) {
            val response = try {
                api.coinListing(apikey, currency)
            } catch (e: IOException) {
                return Result.failure(e)
            } catch (e: HttpException) {
                return Result.failure(e)
            }

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    cachedData = body
                    DataRepository.data = cachedData
                    isDataFetched = true
                } else {
                    return Result.failure(Throwable("Response body is null"))
                }
            } else {
                return Result.failure(Throwable("Response was not successful, code: ${response.code()}"))
            }
        }

        return Result.success(cachedData ?: emptyList())
    }
}