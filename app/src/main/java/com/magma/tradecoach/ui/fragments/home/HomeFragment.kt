package com.magma.tradecoach.ui.fragments.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.magma.tradecoach.adds.AddServices
import com.magma.tradecoach.databinding.FragmentHomeBinding
import com.magma.tradecoach.di.observeUserData
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.networking.DataRepository
import com.magma.tradecoach.ui.fragments.currencies.CurrenciesFragment
import com.magma.tradecoach.ui.segmentPurchase.PurchaseActivity
import com.magma.tradecoach.utilities.BaseFragment
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.utilities.Utils.setFragment
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment() {
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
    viewModel.getUserData()
    Utils.setRecycler(binding.topRecyclerView,adapter)
    DataRepository.getHomeData()?.let { adapter.setData(it) }
    listeners()
    with(viewModel){
        observeUserData(currentUser,::onUserDataLoaded)
    }

    }
    private fun listeners(){
        with(binding){
            shimmer.setOnClickListener {
                setFragment(getMainActivity(),CurrenciesFragment(),2)
            }
            buttonBonusCons.setOnClickListener {
                AddServices().loadRewardedAd(getMainActivity())


            }
            jarImg.setOnClickListener {
                val intent  = Intent(requireContext(),PurchaseActivity::class.java)
                startActivity(intent)

            }
            premiumButton.setOnClickListener {
//                billingManager.purchaseSubscription(Constants.SUBSCRIPTION_ID)

            }
        }

    }
    @SuppressLint("SetTextI18n")
    private fun onUserDataLoaded(user: UserDataModel?){
        if (user?.isPremium == false) {
            showBottomSheet("It looks like you are not a premium member yet!","for only 10 dollars you can become a premium member and enjoy the benefits of trade coach")
        }
        if (Utils.didShowLogin == false) {
            user?.streak?.let { showConsecutiveDayLogin("loresipum", it) }
            Utils.didShowLogin = true
        }
        with(binding){
            dateDotText.text = user?.streak.toString()
            titleTV.text = "Balanse: " + String.format("%.3f",user?.currency)
            usernameTV.text = user?.username
            println(user?.isPremium)
            println(user?.uid)
        }

    }

}