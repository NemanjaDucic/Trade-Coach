package com.magma.tradecoach.ui.fragments.dedication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magma.tradecoach.databinding.FragmentCurrenciesBinding
import com.magma.tradecoach.databinding.FragmentDedicationBinding

class DedicationFragment:Fragment() {
    private lateinit var binding:FragmentDedicationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDedicationBinding.inflate(inflater)


        return binding.root
    }
}