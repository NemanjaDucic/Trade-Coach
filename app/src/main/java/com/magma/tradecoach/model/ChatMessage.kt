package com.magma.tradecoach.model

import com.magma.tradecoach.utilities.Utils

class ChatMessage {
    var text: String? = null
    var sender: String? = null
    var time: String? = Utils.getCurrentDateTime()
    var senderName: String? = null

    constructor()

    constructor(text: String?, sender: String?, time: String, senderName: String?) {
        this.text = text
        this.sender = sender

        if (time == "") this.time = Utils.getCurrentDateTime() else this.time = time
        this.senderName = senderName
    }
}
