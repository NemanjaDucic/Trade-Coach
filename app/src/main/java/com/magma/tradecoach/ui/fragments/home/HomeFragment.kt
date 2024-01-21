package com.magma.tradecoach.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magma.tradecoach.databinding.FragmentHomeBinding
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.ui.fragments.currencies.CurrenciesFragment
import com.magma.tradecoach.ui.segmentPurchase.PurchaseActivity
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.utilities.Utils.setFragment
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        init()

        return binding.root
    }

    private fun init() {
        adapter = HomeAdapter(arrayListOf())
        Utils.setRecycler(binding.topRecyclerView, adapter)
        adapter.setData(DataRepository.homeData())
        listeners()
    }

    private fun listeners() {
        binding.investButton.setOnClickListener {
            setFragment(requireActivity(), CurrenciesFragment(), 2)
        }
        binding.buttonBonusCons.setOnClickListener {
            viewModel.getUserData()

        }
        binding.jarImg.setOnClickListener {
            context?.let {
                Utils.intent(it, PurchaseActivity::class.java)
            }
        }
    }
}