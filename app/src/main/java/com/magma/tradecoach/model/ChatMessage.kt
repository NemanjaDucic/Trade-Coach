package com.magma.tradecoach.model

import com.magma.tradecoach.utilities.Utils

data class ChatMessage(
    var text: String? = null,
    var sender: String? = null,
    var time: String? = Utils.getCurrentDateTime(),
    var senderName: String? = null
)
