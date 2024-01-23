package com.magma.tradecoach.ui.fragments.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magma.tradecoach.R
import com.magma.tradecoach.databinding.FragmentCommunityBinding
import com.magma.tradecoach.ui.fragments.community.chat.ChatFragment
import com.magma.tradecoach.ui.fragments.community.feed.FeedFragment
import com.magma.tradecoach.ui.fragments.community.leaderboard.LeaderboardFragment
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment: Fragment() {
    private lateinit var binding:FragmentCommunityBinding
    private val viewModel:MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater)

        init()
        return binding.root
    }

    private fun init() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeedFragment()).commit()
        binding.bubbleTabBartop.addBubbleListener { id ->
            var selectedFragment: Fragment? = null
            when (id) {
                R.id.feed -> {
                    selectedFragment = FeedFragment()

                }
                R.id.lBoard -> {
                    selectedFragment = LeaderboardFragment()
                }
                R.id.chat -> {
                    selectedFragment = ChatFragment()
                }
            }
            if (selectedFragment != null) childFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, selectedFragment).commit()
        }
    }
}