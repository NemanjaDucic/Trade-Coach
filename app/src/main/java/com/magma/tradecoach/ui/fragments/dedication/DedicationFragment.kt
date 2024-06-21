package com.magma.tradecoach.ui.fragments.dedication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.hbb20.CountryCodePicker
import com.magma.tradecoach.R
import com.magma.tradecoach.adapters.AdsAdapter
import com.magma.tradecoach.adapters.LoginsAdapter
import com.magma.tradecoach.adapters.TransAdapter
import com.magma.tradecoach.databinding.FragmentCurrenciesBinding
import com.magma.tradecoach.databinding.FragmentDedicationBinding
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.Constants
import com.magma.tradecoach.utilities.CountryUtils
import com.magma.tradecoach.utilities.PrefSingleton
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DedicationFragment:BaseFragment() {
    private lateinit var binding:FragmentDedicationBinding
    private val viewModel: MainViewModel by viewModels()

    private var isAdLoaded = false

    private lateinit var adView: AdView
    private lateinit var loginsAdapter: LoginsAdapter
    private lateinit var adsAdapter: AdsAdapter
    private lateinit var transAdapter: TransAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDedicationBinding.inflate(inflater)
    init()

        return binding.root
    }
    private fun init(){
        viewModel.getUserData()

        viewModel.currentUser.observe(requireActivity()){
            user ->
            loginsAdapter = LoginsAdapter(binding.recyclerLogins,user?.streak!!)
            adsAdapter = AdsAdapter(binding.recyclerAds,user?.addsWatched!!)
            transAdapter = TransAdapter(binding.recyclerTrans,user?.transactionsCompleted!!)
            binding.recyclerAds.adapter = adsAdapter
            binding.recyclerLogins.adapter = loginsAdapter
            binding.recyclerTrans.adapter = transAdapter
            binding.recyclerAds.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerTrans.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerLogins.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            loginsAdapter.scrollToConsecutiveDay()
            transAdapter.scrollToConsecutiveDay()
            adsAdapter.scrollToConsecutiveDay()
            binding.tvName.text = "Name: " +  user.username
            binding.phoneTV.text = user.emailAddress
            binding.flagImg.setCountryForNameCode(CountryUtils.getCountryCode(user.country!!))


        }

        adView = (getMainActivity()).getAdView()
        if (adView.adSize != null && !isAdLoaded) {
            binding.adViewContainer.addView(adView)
            isAdLoaded = true
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding.adViewContainer.removeView(adView)
    }
}