package com.magma.tradecoach.ui.fragments.community.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentChatBinding

class ChatFragment: Fragment() {

    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)


        return binding.root
    }
}