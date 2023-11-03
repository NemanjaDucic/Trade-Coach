package com.magma.tradecoach.hilt.modules

import com.magma.tradecoach.networking.CoinMarketApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val base = "https://api.coingecko.com/api/v3/"

    @Singleton
    @Provides
    fun provideNewApiInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit):CoinMarketApi{
        return retrofit.create(CoinMarketApi::class.java)
    }

}