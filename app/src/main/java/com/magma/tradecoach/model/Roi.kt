package com.magma.tradecoach.model

data class Roi(
    val currency: String ?= "",
    val percentage: Double ?= 0.0,
    val times: Double ?= 0.0
)
