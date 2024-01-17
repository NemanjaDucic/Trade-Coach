package com.magma.tradecoach.model

import com.google.firebase.database.PropertyName

data class UserDataModel(
    val username:String ?= "",
    val uid :String ?= "",
    val emailAddress: String ?= "",
    val country:String ?= "",
    var currency :Double ?= 0.9,
    @get:PropertyName("isPremium")
    val isPremium: Boolean? = false,
    val bonusPoints:Int ?= 0,
    val lastDate: String ?= "",
    val coins: ArrayList<CoinModel> ?= arrayListOf()
)
