package com.magma.tradecoach.ui.fragments.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentCommunityBinding
import com.magma.tradecoach.databinding.FragmentCurrenciesBinding

class CurrenciesFragment:Fragment() {
    private lateinit var binding:FragmentCurrenciesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrenciesBinding.inflate(inflater)


        return binding.root
    }

    private fun  init(){
    }
}