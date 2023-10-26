package com.magma.tradecoach.model

data class UserDataModel(
    val username:String ?= "",
    val uid :String ?= "",
    val emailAddress: String ?= "",
    val country:String ?= "",
    val currency :Double ?= 0.9
)
