package com.magma.tradecoach.ui.segmentMain

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.magma.tradecoach.databinding.ActivityMainBinding
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity: AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var valu: List<MarketCoinModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }
    private fun init(){
        binding.logo.setOnClickListener {
            println(DataRepository.data)
        }
    }
}