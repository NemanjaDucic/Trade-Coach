package com.magma.tradecoach.viewmodel

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.magma.tradecoach.BaseApplication.Companion.applicationContext
import com.magma.tradecoach.R
import com.magma.tradecoach.model.ChatMessage
import com.magma.tradecoach.ui.fragments.community.chat.ChatViewHolder
import com.magma.tradecoach.utilities.PrefSingleton
import java.util.*

class ChatViewModel: ViewModel() {
    val observeAdapter = MutableLiveData<FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>>()

    private val context = applicationContext()

    private var adapter: FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>? = null
    private val options: FirebaseRecyclerOptions<ChatMessage>

    init {
        val query = FirebaseDatabase.getInstance().reference.child("chat").limitToLast(50)
        options = FirebaseRecyclerOptions.Builder<ChatMessage>()
            .setQuery(query, ChatMessage::class.java)
            .build()

        displayChatMessage()
    }

    fun displayChatMessage() {
        adapter = object : FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChatViewHolder {
                return ChatViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.chat_item, viewGroup, false)
                )
            }

            override fun onBindViewHolder(
                viewHolder: ChatViewHolder,
                position: Int,
                model: ChatMessage
            ) {
                val messageText = model.text
                val messageTime = model.time?.let { splitTime(it) }
                val messageSenderID = model.sender
                val messageSenderName = model.senderName
                if (messageSenderID == PrefSingleton.instance?.getString("id")) {
                    viewHolder.theirMessage.visibility = View.INVISIBLE
                    viewHolder.myMessage.visibility = View.VISIBLE
                    viewHolder.myMessage.background =
                        ContextCompat.getDrawable(context, R.drawable.my_message_item)
                    viewHolder.tvMyText.text = messageText
                    viewHolder.tvMyTime.text = messageTime
                } else {
                    viewHolder.theirMessage.visibility = View.VISIBLE
                    viewHolder.myMessage.visibility = View.INVISIBLE
                    viewHolder.tvTheirUsername.text = messageSenderName
                    viewHolder.tvTheirText.text = messageText
                    viewHolder.tvTheirTime.text = messageTime
                    messageSenderName?.let { setAvatarInitials(it, viewHolder.iTheirImage) }
                }
            }

            override fun onError(error: DatabaseError) {
                Log.d("TAG_ANDRA", "Error " + error.message)
            }
        }

        adapter?.startListening()
        observeAdapter.value = adapter!!
    }

    private fun setAvatarInitials(username: String, imageView: ImageView) {
        val safeUsername = if (username.length > 2) username else "Tesla Coiner"
        val b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.drawColor(Color.parseColor("#313131"))
        val paint = Paint()
        paint.textSize = 46f
        paint.color = Color.parseColor("#FFDC64")
        c.drawText(safeUsername.substring(0, 2).uppercase(Locale.getDefault()), 22f, 65f, paint)
        imageView.setImageBitmap(b)
    }

    private fun splitTime(time: String): String {
        return try {
            val split = time.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            split[1]
        } catch (e: Exception) {
            " "
        }
    }
}