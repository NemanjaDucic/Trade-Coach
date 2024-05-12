package com.magma.tradecoach.interfaces

interface SellCoinsCallback {
    fun onSellSuccess(totalEarning: Double)
    fun onSellFailure(message: String)
}