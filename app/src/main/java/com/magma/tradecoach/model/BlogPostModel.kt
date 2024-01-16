package com.magma.tradecoach.model

data class BlogPostModel(
    val author:String ?= "",
    val postTitle :String ?= "",
    val postContent:String ?= "",
    val id :String ?= "",
    val date:String ?= ""
)
