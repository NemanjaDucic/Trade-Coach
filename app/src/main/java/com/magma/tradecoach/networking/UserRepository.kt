package com.magma.tradecoach.networking

import com.magma.tradecoach.model.CoinInfoModel
import com.magma.tradecoach.model.CoinModel
import com.magma.tradecoach.model.UserDataModel
import javax.inject.Inject

class UserRepository @Inject constructor(){
    suspend fun sell(user:UserDataModel){
    }
    suspend fun buy(user:UserDataModel){

    }
    suspend fun updateUser(user:UserDataModel){

    }
    fun getUserCurrency(user:UserDataModel) : ArrayList<CoinModel>? {
        return user.coins
    }
    fun sortAndCountUserCoins(user: UserDataModel): ArrayList<CoinInfoModel>? {
        val coins = user.coins ?: return null

        val sortedCoins = coins.sortedBy { it.name }
        val coinNameCountMap = coins.groupingBy { it.name }.eachCount()

        return sortedCoins.map { coin ->
            CoinInfoModel(coin, coinNameCountMap[coin.name] ?: 0)
        } as ArrayList<CoinInfoModel>
    }
}