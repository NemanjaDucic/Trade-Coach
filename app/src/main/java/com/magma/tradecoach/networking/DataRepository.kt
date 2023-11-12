package com.magma.tradecoach.networking

import com.magma.tradecoach.model.HomeItemModel
import com.magma.tradecoach.model.MarketCoinModel


object DataRepository {
        var data : List<MarketCoinModel> ?= emptyList()
        fun homeData() : ArrayList<HomeItemModel>{
                return arrayListOf( HomeItemModel(
                        DataRepository.data?.get(0)?.symbol!!.uppercase(),DataRepository.data?.get(0)?.name!!,"$ " + DataRepository.data?.get(0)?.current_price.toString(),
                        DataRepository.data?.get(0)?.price_change_24h.toString() + "%"
                ),HomeItemModel(
                        DataRepository.data?.get(1)?.symbol!!.uppercase(),DataRepository.data?.get(1)?.name!!,"$ " + DataRepository.data?.get(1)?.current_price.toString(),
                        DataRepository.data?.get(1)?.price_change_24h.toString() + "%"
                ),HomeItemModel(
                        DataRepository.data?.get(2)?.symbol!!.uppercase(),DataRepository.data?.get(2)?.name!!,"$ " + DataRepository.data?.get(2)?.current_price.toString(),
                        DataRepository.data?.get(2)?.price_change_24h.toString() + "%"
                ))
        }

}
