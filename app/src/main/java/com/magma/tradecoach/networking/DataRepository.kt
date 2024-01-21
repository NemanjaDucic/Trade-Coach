package com.magma.tradecoach.networking

import com.magma.tradecoach.model.HomeItemModel
import com.magma.tradecoach.model.MarketCoinModel

object DataRepository {
        var data: List<MarketCoinModel>? = emptyList()

        //TODO Verovatno treba ovde da merge onaj for?
        fun homeData(): ArrayList<HomeItemModel> {
                return arrayListOf(
                        HomeItemModel(
                                data?.get(0)?.symbol?.uppercase().toString(),
                                data?.get(0)?.name,
                                "$ " + data?.get(0)?.currentPrice.toString(),
                                data?.get(0)?.priceChange24h.toString() + "%"
                        ), HomeItemModel(
                                data?.get(1)?.symbol?.uppercase().toString(),
                                data?.get(1)?.name,
                                "$ " + data?.get(1)?.currentPrice.toString(),
                                data?.get(1)?.priceChange24h.toString() + "%"
                        ), HomeItemModel(
                                data?.get(2)?.symbol?.uppercase().toString(),
                                data?.get(2)?.name,
                                "$ " + data?.get(2)?.currentPrice.toString(),
                                data?.get(2)?.priceChange24h.toString() + "%"
                        )
                )
        }

        fun coinsData(): ArrayList<MarketCoinModel> {
                return data as ArrayList<MarketCoinModel>
        }

        fun getCoinsByGrowth(): ArrayList<MarketCoinModel> {
                return data?.sortedBy { it.athChangePercentage } as ArrayList<MarketCoinModel>
        }
}
