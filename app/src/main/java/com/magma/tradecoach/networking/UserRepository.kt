package com.magma.tradecoach.networking

import com.magma.tradecoach.model.CoinModel
import com.magma.tradecoach.model.UserDataModel
import javax.inject.Inject

class UserRepository @Inject constructor(){


    fun sortAndCountUserCoins(user: UserDataModel): ArrayList<CoinModel>? {
        val coins = user.coins ?: return null

        val sortedCoins = coins.values.sortedBy { it.quantity }

        return sortedCoins as ArrayList<CoinModel>
    }
}