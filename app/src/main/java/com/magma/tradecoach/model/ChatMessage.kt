package com.magma.tradecoach.model

import com.magma.tradecoach.ext.formatted
import java.util.Date

data class ChatMessage(
    var text: String? = null,
    var sender: String? = null,
    var time: String? = Date().formatted,
    var senderName: String? = null
)
