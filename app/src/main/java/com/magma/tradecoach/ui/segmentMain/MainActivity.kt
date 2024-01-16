package com.magma.tradecoach.ui.segmentMain

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.magma.tradecoach.R
import com.magma.tradecoach.databinding.ActivityMainBinding
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.ui.fragments.community.CommunityFragment
import com.magma.tradecoach.ui.fragments.currencies.CurrenciesFragment
import com.magma.tradecoach.ui.fragments.dedication.DedicationFragment
import com.magma.tradecoach.ui.fragments.home.HomeFragment
import com.magma.tradecoach.utilities.ConsecutiveDayChecker
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        viewModel.getCoins()
        viewModel.initialCurrencyListLiveData.observe(this){
            value->
            updateUIWithData(value)
        }
        ConsecutiveDayChecker(this).onUserLogin()
        binding.bubbleTabBar.addBubbleListener { id ->
            var selectedFragment: Fragment? = null
            when (id) {
                R.id.home -> {
                    selectedFragment = HomeFragment()

                }
                R.id.community -> {
                    selectedFragment = CommunityFragment()
                }
                R.id.currencies -> {
                    selectedFragment = CurrenciesFragment()
                }
                R.id.dedication -> {
                    selectedFragment = DedicationFragment()
                }

            }
            if (selectedFragment != null) supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, selectedFragment).commit()
        }
        this.onBackPressedDispatcher.addCallback(this) {
            supportFragmentManager.popBackStack()

        }

    }

    private fun updateUIWithData(data: List<MarketCoinModel>) {
        val gson = Gson()
        val json = gson.toJson(data)
        val arguments = Bundle()
       arguments.putString("key",json)
        HomeFragment().arguments = arguments
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()
    }

}