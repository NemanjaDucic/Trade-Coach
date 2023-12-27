package com.magma.tradecoach.ui.fragments.community.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var myMessage: ConstraintLayout
    var theirMessage: ConstraintLayout
    var theirContent: ConstraintLayout
    var tvMyTime: TextView
    var tvTheirTime: TextView
    var tvTheirUsername: TextView
    var tvMyText: TextView
    var tvTheirText: TextView
    var iTheirImage: ImageView

    init {
        myMessage = itemView.findViewById<View>(R.id.myMessage) as ConstraintLayout
        theirMessage = itemView.findViewById<View>(R.id.theirMessage) as ConstraintLayout
        theirContent = itemView.findViewById<View>(R.id.consTheirMessageContent) as ConstraintLayout
        tvMyText = itemView.findViewById<View>(R.id.tMyText) as TextView
        tvMyTime = itemView.findViewById<View>(R.id.tMyTime) as TextView
        tvTheirText = itemView.findViewById<View>(R.id.tTheirText) as TextView
        tvTheirTime = itemView.findViewById<View>(R.id.tTheirTime) as TextView
        tvTheirUsername = itemView.findViewById<View>(R.id.tUsernameTheirMessage) as TextView
        iTheirImage = itemView.findViewById<View>(R.id.iMessage) as ImageView
    }
}