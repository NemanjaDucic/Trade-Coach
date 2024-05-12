package com.magma.tradecoach.ui.fragments.currencies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.magma.tradecoach.R
import com.magma.tradecoach.adapters.MyPossessionsAdapter
import com.magma.tradecoach.databinding.FragmentCurrenciesBinding
import com.magma.tradecoach.interfaces.CurrencyListener
import com.magma.tradecoach.interfaces.MyCoinClickedInterface
import com.magma.tradecoach.model.CoinModel
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.ui.fragments.home.HomeFragment
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment :BaseFragment(),CurrencyListener,MyCoinClickedInterface {
    private lateinit var binding:FragmentCurrenciesBinding
    private lateinit var myCurrAdapter: CurrenciesAdapter
    private var menuButtonAreVisible = true
    private var flag = false
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
        getMainActivity().onBackPressedDispatcher.addCallback(this) {
           Utils.setFragment(requireActivity(),HomeFragment(),0)
        }
        myCurrAdapter = CurrenciesAdapter(arrayListOf(),this)
        Utils.setRecycler(binding.currenciesRV,myCurrAdapter)
        DataRepository.coinsData()?.let { myCurrAdapter.setData(it) }
        listeners()
        viewModel.getCoins()
        viewModel.getUserData()
        sellAdapter = MyPossessionsAdapter( arrayOf(),this)
        viewModel.currentUser.observe(viewLifecycleOwner){user ->
            val coins = user?.coins?.values?.toTypedArray()

            coins?.let { sellAdapter.setData(it) }

        }


    }
    @SuppressLint("SetTextI18n")
    private fun listeners(){

        with(binding){
            amountButton.setOnClickListener {
                changeButtonAppearance(amountButton)
                showUsersMagmaCoins(viewModel.currentUser.value?.currency.toString())
            }
            growthButton.setOnClickListener {
                val descendingGrowth = viewModel.initialCurrencyListLiveData.value?.sortedByDescending { it.marketCapChange24h }
                descendingGrowth?.let {
                    myCurrAdapter.setData(ArrayList(it))
                }
                changeButtonAppearance(growthButton)
                setMenuState(false)
            }
            possessionButton.setOnClickListener {
                showPossFragment()
               changeButtonAppearance(possessionButton)
            }
            menuButton.setOnClickListener {
                setMenuState(menuButtonAreVisible)
            }

            stockImage.setOnClickListener {
                val ascendingImageDrawable = ContextCompat.getDrawable(getMainActivity(), R.drawable.stock_up)
                val descendingImageDrawable = ContextCompat.getDrawable(getMainActivity(), R.drawable.stock_dw)
                if (!flag){
                    val descendingGrowth = viewModel.initialCurrencyListLiveData.value?.sortedByDescending { it.marketCapChange24h }
                    descendingGrowth?.let {
                        myCurrAdapter.setData(ArrayList(it))
                        Glide.with(getMainActivity()).load(descendingImageDrawable).into(stockImage)
                    }
                } else {
                    val ascendingGrowth = viewModel.initialCurrencyListLiveData.value?.sortedBy { it.marketCapChange24h }
                    ascendingGrowth?.let {
                        myCurrAdapter.setData(ArrayList(it))
                        Glide.with(getMainActivity()).load(ascendingImageDrawable).into(stockImage)

                    }
                }
                flag = !flag
            }

        }

    }
   private fun showPossFragment(){
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_possession, null)
        val recycler: RecyclerView = dialogView.findViewById(R.id.possesionsRV)
        recycler.adapter = sellAdapter
        recycler.layoutManager = LinearLayoutManager(getMainActivity(),
            LinearLayoutManager.VERTICAL,false)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
    }
    private fun setMenuState(isActive: Boolean) {
        when (isActive) {
            true -> {
                Utils.animateViewFromBottom(binding.buttonsContainer)
                Utils.animateViewAppear(binding.blurView)
            }
            false -> {
                Utils.animateViewToBottom(binding.buttonsContainer)
                binding.blurView.isVisible = false
            }
        }
        menuButtonAreVisible = !menuButtonAreVisible
    }
    override fun getCoin(coin: MarketCoinModel) {
        showTradeSheet(coin,viewModel.currentUser.value!!)
    }
   private fun changeButtonAppearance(button:Button){
        val buttons = arrayListOf(binding.growthButton,binding.possessionButton,binding.amountButton)
        for (btn in buttons){
            if (btn != button)  {
                btn.setBackgroundResource(R.drawable.hover_button)
            } else {
                btn.setBackgroundResource(R.drawable.button)

            }
        }
    }

    override fun coinClicked(coin: CoinModel) {
        coin.name?.let { showSellSheet(it,coin.quantity!!) }

    }

}