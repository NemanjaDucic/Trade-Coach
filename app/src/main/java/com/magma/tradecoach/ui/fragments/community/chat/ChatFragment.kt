package com.magma.tradecoach.ui.fragments.community.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.magma.tradecoach.databinding.FragmentChatBinding
import com.magma.tradecoach.di.observe
import com.magma.tradecoach.model.ChatMessage
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.DatabaseProvider
import com.magma.tradecoach.utilities.SessionManager
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.ChatViewModel

class ChatFragment: BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        init()

        return binding.root
    }
    private fun init() {
        binding.recChat.layoutManager = LinearLayoutManager(context)
        binding.recChat.setHasFixedSize(true)

        with(viewModel) {
            observe(observeAdapter, ::onAdapterUpdated)
        }

        listeners()
    }
    private fun onAdapterUpdated(adapter: FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>?) {
        if (binding.recChat.adapter == null)
            adapter?.let {
                binding.recChat.adapter = adapter
            }
    }

    private fun resetUI() {
        binding.eMessage.setText("")
        binding.recChat.requestFocus()
        binding.tSendMessage.visibility = View.INVISIBLE
        binding.recChat.scrollToPosition((binding.recChat.adapter?.itemCount ?: 1) - 1)

        if (progressDialog.isShowing) progressDialog.dismiss()
    }

    private fun listeners() {
        binding.tSendMessage.setOnClickListener {
            if (!Utils.isETEmpty(binding.eMessage)) {
                val message = Utils.getETText(binding.eMessage)
                DatabaseProvider().sendMessage(message,SessionManager.getId())

                viewModel.displayChatMessage()

                resetUI()
            }
        }

        binding.eMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.tSendMessage.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}
