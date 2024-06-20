package com.magma.tradecoach.model


data class UserDataModel(
    var coins: HashMap<String, CoinModel>? = null,
    var country: String? = null,
    var currency: Double? = 0.000,
    var emailAddress: String? = null,
    @JvmField
    var isPremium: Boolean? = null,
    var streak: Int? = null,
    var uid: String? = null,
    var username: String? = null,
    var lastDate: String? = null ,
    var addsWatched: Int?= 0,
    var transactionsCompleted : Int?= 0// Assuming lastDate is stored as a timestamp (Long)
)
