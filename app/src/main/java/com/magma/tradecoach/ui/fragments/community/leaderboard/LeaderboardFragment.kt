package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.tradecoach.R
import com.magma.tradecoach.R.color.gray
import com.magma.tradecoach.databinding.FragmentLeaderboardBinding
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : BaseFragment() {

    private lateinit var adapter: LeaderboardAdapter
    private lateinit var binding: FragmentLeaderboardBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        initViews()
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun initViews() {
        binding.loginTV.setBackgroundResource(R.drawable.blue_button)
        adapter = LeaderboardAdapter(arrayListOf())
        binding.leaderboardRV.apply {
            adapter = this@LeaderboardFragment.adapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun setupListeners() {
        binding.loginTV.setOnClickListener { handleFilterClicked(binding.loginTV, "Login") }
        binding.adsTV.setOnClickListener { handleFilterClicked(binding.adsTV, "Ads") }
        binding.transTV.setOnClickListener { handleFilterClicked(binding.transTV, "Trans") }
    }

    private fun observeViewModel() {
        viewModel.getUsers()
        viewModel.topUsersLiveData.observe(viewLifecycleOwner) { userArray ->
            updateUI(userArray)
            adapter.setData(userArray.drop(3))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(userArray: List<UserDataModel>) {
        if (userArray.size >= 3) {
            with(binding) {
                tvMid.text = "${userArray[0].username}\n${userArray[0].streak}"
                tvRight.text = "${userArray[2].username}\n${userArray[2].streak}"
                tvLeft.text = "${userArray[1].username}\n${userArray[1].streak}"
            }
        }
    }

    private fun handleFilterClicked(view: TextView, mode: String) {
        resetTextViews()
        view.setBackgroundResource(R.drawable.blue_button)
        view.setTextColor(Color.WHITE)
        adapter.mode = mode

        val sortedArray = when (mode) {
            "Login" -> viewModel.topUsersLiveData.value?.sortedByDescending { it.streak }
            "Ads" -> viewModel.topUsersLiveData.value?.sortedByDescending { it.addsWatched }
            "Trans" -> viewModel.topUsersLiveData.value?.sortedByDescending { it.transactionsCompleted }
            else -> null
        }

        sortedArray?.let {
            binding.tvMid.text = "${it[0].username}\n${getColumnValue(mode, it[0])}"
            binding.tvRight.text = "${it[2].username}\n${getColumnValue(mode, it[2])}"
            binding.tvLeft.text = "${it[1].username}\n${getColumnValue(mode, it[1])}"
        }

        when (mode) {
            "Login" -> adapter.setData(setFilterLogin())
            "Ads" -> adapter.setData(setFilterAds())
            "Trans" -> adapter.setData(setFilterTransactions())
        }
    }

    private fun getColumnValue(mode: String, user: UserDataModel): String {
        return when (mode) {
            "Login" -> user.streak.toString()
            "Ads" -> user.addsWatched.toString()
            "Trans" -> user.transactionsCompleted.toString()
            else -> ""
        }
    }

    private fun resetTextViews() {
        with(binding) {
            adsTV.setBackgroundResource(R.drawable.transparent_bcg)
            loginTV.setBackgroundResource(R.drawable.transparent_bcg)
            transTV.setBackgroundResource(R.drawable.transparent_bcg)
            adsTV.setTextColor(Color.GRAY)
            loginTV.setTextColor(Color.GRAY)
            transTV.setTextColor(Color.GRAY)

        }
    }

    private fun setFilterLogin(): List<UserDataModel> {
        return viewModel.topUsersLiveData.value?.sortedByDescending { it.streak }?.drop(3)
            ?: emptyList()
    }

    private fun setFilterAds(): List<UserDataModel> {
        return viewModel.topUsersLiveData.value?.sortedByDescending { it.addsWatched }?.drop(3)
            ?: emptyList()
    }

    private fun setFilterTransactions(): List<UserDataModel> {
        return viewModel.topUsersLiveData.value?.sortedByDescending { it.transactionsCompleted }
            ?.drop(3) ?: emptyList()
    }
}
