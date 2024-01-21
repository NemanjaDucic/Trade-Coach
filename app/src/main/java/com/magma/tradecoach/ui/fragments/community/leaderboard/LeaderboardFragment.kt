package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentLeaderboardBinding
import com.magma.tradecoach.model.LeaderboardModel
import com.magma.tradecoach.utilities.Utils

class LeaderboardFragment:Fragment() {
    private lateinit var adapter: LeaderboardAdapter
    private lateinit var binding: FragmentLeaderboardBinding
    private val holderArray = arrayListOf(
        LeaderboardModel(1, "somename", 500),
        LeaderboardModel(1, "somename", 500),
        LeaderboardModel(1, "somename", 500)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater)
        init()

        return binding.root
    }

    private fun init() {
        adapter = LeaderboardAdapter(holderArray)
        Utils.setRecycler(binding.leaderboardRV, adapter)
    }
}