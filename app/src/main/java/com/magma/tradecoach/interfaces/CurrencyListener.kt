package com.magma.tradecoach.interfaces

import com.magma.tradecoach.model.MarketCoinModel

interface CurrencyListener {
    fun getCoin(coin: MarketCoinModel)
}