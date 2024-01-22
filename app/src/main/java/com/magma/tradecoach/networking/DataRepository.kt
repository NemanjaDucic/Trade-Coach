package com.magma.tradecoach.networking

import com.magma.tradecoach.model.HomeItemModel
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.utilities.Constants


object DataRepository {
        var data: List<MarketCoinModel>? = emptyList()
        fun getHomeData(): ArrayList<HomeItemModel>? {
                val homeDataList = ArrayList<HomeItemModel>()

                for (i in 0 until minOf(data?.size ?: 0, Constants.HOME_ITEM_LIMIT)) {
                        data?.get(i)?.let {
                                val homeItem = HomeItemModel(
                                        it.symbol.uppercase(),
                                        it.name,
                                        "$ ${it.current_price}",
                                        "${it.price_change_24h}%"
                                )
                                homeDataList.add(homeItem)
                        }
                }

                return homeDataList
        }
        fun coinsData(): ArrayList<MarketCoinModel>? {
                return  data as ArrayList<MarketCoinModel>
        }
        fun getCoinsByGrowth():ArrayList<MarketCoinModel>?{
                return data?.sortedBy { it.atl_change_percentage } as ArrayList<MarketCoinModel>
        }

}
