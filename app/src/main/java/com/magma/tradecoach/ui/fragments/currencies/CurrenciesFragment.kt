package com.magma.tradecoach.ui.fragments.currencies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magma.tradecoach.R
import com.magma.tradecoach.databinding.FragmentCurrenciesBinding
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.networking.UserRepository
import com.magma.tradecoach.ui.fragments.home.HomeFragment
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.Util

@AndroidEntryPoint
class CurrenciesFragment :Fragment() {
    private lateinit var binding:FragmentCurrenciesBinding
    private lateinit var myCurrAdapter: CurrenciesAdapter
    private var flag = false
    private var isMyButtonActive = true

    private val viewModel:MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrenciesBinding.inflate(inflater)
        init()
        return binding.root
    }
    private fun  init(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
           Utils.setFragment(requireActivity(),HomeFragment(),0)
        }
        myCurrAdapter = CurrenciesAdapter(arrayListOf())
        Utils.setRecycler(binding.currenciesRV,myCurrAdapter)
        DataRepository.coinsData()?.let { myCurrAdapter.setData(it) }
        listeners()
    }
    @SuppressLint("SetTextI18n")
    private fun listeners(){
        binding.menuButton.setOnClickListener {
          if (flag){
              hideButtons()
          } else {
              showButtons()
          }
            flag = !flag
        }
        binding.amountButton.setOnClickListener {

        }
        binding.growthButton.setOnClickListener {

        }
        binding.possessionButton.setOnClickListener {
            if (isMyButtonActive){
                buttonsAreActive()
                viewModel.getUsersCoins()
            } else {
                buttonsAreInactive()
                viewModel.initialCurrencyListLiveData.observe(this){
                    coins ->
                    coins.sortedBy { it.market_cap_rank }
                }
            }
            isMyButtonActive = !isMyButtonActive
        }
    }
    private fun showButtons(){
        Utils.animateViewFromBottom(binding.buttonsContainer)
        Utils.animateViewAppear(binding.blurView)

    }
    private fun hideButtons(){
        Utils.animateViewToBottom(binding.buttonsContainer)
        binding.blurView.isVisible = false
    }
    private fun buttonsAreActive(){
        binding.amountButton.isEnabled = true
        binding.possessionButton.setBackgroundResource(R.drawable.button)
    }
    private fun buttonsAreInactive(){
        binding.amountButton.isEnabled = false
        binding.possessionButton.setBackgroundResource(R.drawable.button)
    }

}