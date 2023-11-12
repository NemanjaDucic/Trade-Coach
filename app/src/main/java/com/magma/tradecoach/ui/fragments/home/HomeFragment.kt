package com.magma.tradecoach.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentHomeBinding
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.utilities.Utils

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        init()

        return binding.root
    }
private fun init(){
    adapter = HomeAdapter(arrayListOf())
    Utils.setRecycler(binding.topRecyclerView,adapter)
    adapter.setData(DataRepository.homeData())
    }
}