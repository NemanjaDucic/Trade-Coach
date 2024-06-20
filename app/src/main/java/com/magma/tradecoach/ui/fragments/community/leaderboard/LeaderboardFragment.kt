package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.tradecoach.databinding.FragmentLeaderboardBinding
import com.magma.tradecoach.model.UserWithCombinedValue
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment:BaseFragment() {
    private lateinit var adapter: LeaderboardAdapter
    private lateinit var binding: FragmentLeaderboardBinding

    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater)
        init()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        viewModel.getLeaderboard()
        viewModel.getTopUsersCombined()
        adapter = LeaderboardAdapter(arrayListOf())
        binding.leaderboardRV.adapter = adapter
        binding.leaderboardRV.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        viewModel.topUsersLiveData.observe(viewLifecycleOwner){ userArray ->
            with(binding){
                tvMid.text = userArray[0].username + "\n" + getTwoDigitString(userArray[0].currency!!)
                tvRight.text = userArray[1].username + "\n" + getTwoDigitString(userArray[1].currency!!)
                tvLeft.text = userArray[2].username + "\n" +  getTwoDigitString(userArray[2].currency!!)

            }
        }
        viewModel.combinedUsersLiveData.observe(viewLifecycleOwner){
            users ->
            adapter.setData(users ?: arrayListOf())

        }
    }
    private fun getTwoDigitString(number:Double):String{
        return String.format("%.2f", number)
    }
}
