package com.magma.tradecoach.model


data class UserDataModel(
    var coins: HashMap<String,CoinModel>? = null,
    var country: String? = null,
    var currency: Double? = null,
    var emailAddress: String? = null,
    var isPremium: Boolean? = null,
    var streak: Int? = null,
    var uid: String? = null,
    var username: String? = null
)
