package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.magma.tradecoach.R
import com.magma.tradecoach.adapters.ConsecutiveDaysAdapter
import com.magma.tradecoach.adapters.MyPossessionsAdapter
import com.magma.tradecoach.interfaces.SellCoinsCallback
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.ui.segmentMain.MainActivity
import com.magma.tradecoach.viewmodel.MainViewModel

abstract class BaseFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()
     lateinit var sellAdapter :MyPossessionsAdapter

    private val sellCoinsCallback = object : SellCoinsCallback {
        @SuppressLint("NotifyDataSetChanged")
        override fun onSellSuccess(totalEarning: Double) {
            println("Successfully sold coins for $totalEarning.")
             viewModel.getUserData()


                Toast.makeText(getMainActivity(), "Sold successfully", Toast.LENGTH_SHORT).show()



        }

        override fun onSellFailure(message: String) {
            println("Failed to sell coins: $message")
            Toast.makeText(getMainActivity(), "Invalid quantity", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)




    }

    protected fun getMainActivity(): MainActivity {
        return requireActivity() as MainActivity
    }
     fun showBottomSheet(titleText:String,descriptionText:String) {
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_pop_up, null)

        val whiteText: TextView = dialogView.findViewById(R.id.whiteText)
        val xButton: Button = dialogView.findViewById(R.id.dismissButton)
        val descriptionGrayText: TextView = dialogView.findViewById(R.id.grayText)
        val mainButton: Button = dialogView.findViewById(R.id.applyButton)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        whiteText.text = titleText
        descriptionGrayText.text = descriptionText


        mainButton.setOnClickListener {

//           billingManager.purchaseSubscription(Constants.SUBSCRIPTION_ID)
        }
        xButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }
    fun showTradeSheet(coinInfoModel: MarketCoinModel,user:UserDataModel) {
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_options, null)
        val coinsValueComparedToMagma:TextView = dialogView.findViewById(R.id.coinText)
        val userMagmaCoins: TextView = dialogView.findViewById(R.id.whiteText)
        val magmaCoinsEntered: TextView = dialogView.findViewById(R.id.whiteText2)
        val coinImage:ImageView = dialogView.findViewById(R.id.imgCoin)
        val xButton: Button = dialogView.findViewById(R.id.dismissButton)
        val editTextView:EditText = dialogView.findViewById(R.id.numberET)
        val mainButton: Button = dialogView.findViewById(R.id.applyButton)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        editTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println(s)
            }


            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val newText = s.toString()
                if (newText.isEmpty() || newText == null) {
                    return
                } else {
                    val ss = String.format("%.3f",newText.toDouble() * coinInfoModel.currentPrice)
                    magmaCoinsEntered.text =   ss + " ="
                    coinsValueComparedToMagma.text = newText
                    if (newText.toDouble() * coinInfoModel.currentPrice > userMagmaCoins.text.toString().toDouble()){
                        userMagmaCoins.setTextColor(Color.RED)
                    } else {
                        userMagmaCoins.setTextColor(Color.WHITE)

                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {
                println(s)
            }
        })
        magmaCoinsEntered.text = String.format("%.6f",coinInfoModel.currentPrice) + " ="
        Glide.with(getMainActivity()).load(coinInfoModel.image).into(coinImage)
        userMagmaCoins.text = String.format("%.6f",user.currency)
        coinsValueComparedToMagma.text = "1"
        mainButton.setOnClickListener {
                    if (editTextView.text.isNotEmpty()){
                        if(DatabaseProvider().buyCoins(viewModel.currentUser.value!!, coinInfoModel,editTextView.text.toString().toInt())){
                            Toast.makeText(getMainActivity(),"Successfully bought ${editTextView.text} ${coinInfoModel.name}(s)",Toast.LENGTH_SHORT).show()
                            viewModel.getUserData()
                            viewModel.updateNumberOfTransactions()
                            bottomSheetDialog.dismiss()
                        } else {
                            Toast.makeText(getMainActivity(),"Purchase Failed",Toast.LENGTH_SHORT).show()
                            bottomSheetDialog.dismiss()
                        }
                    } else {
                        Toast.makeText(activity,"Please Enter Number",Toast.LENGTH_SHORT).show()
                    }


        }
        xButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun showSellSheet(coinInfoModel: MarketCoinModel, availableCoins:Int) {
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_options, null)
        val userMagmaCoins: TextView = dialogView.findViewById(R.id.whiteText)
        userMagmaCoins.text = String.format("%.6f",viewModel.currentUser.value?.currency)
        val availableCoinsText: TextView = dialogView.findViewById(R.id.whiteText2)
        availableCoinsText.text = availableCoins.toString()

        val valueOfCoinsInMagma:TextView = dialogView.findViewById(R.id.coinText)
        valueOfCoinsInMagma.text = "= " + (availableCoins * coinInfoModel.currentPrice).toString()
        val magmaCoinImage :ImageView = dialogView.findViewById(R.id.imageg2)
        magmaCoinImage.isVisible = false

        val coinImage:ImageView = dialogView.findViewById(R.id.imgCoin)


        val xButton: Button = dialogView.findViewById(R.id.dismissButton)
        val editTextView:EditText = dialogView.findViewById(R.id.numberET)
        val mainButton: Button = dialogView.findViewById(R.id.applyButton)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        editTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val newText = s.toString()
                if (newText.isEmpty() || newText == null) {
                    return
                } else {

                    if (newText.toInt() > availableCoins){
                        userMagmaCoins.setTextColor(Color.RED)

                    } else {
                        userMagmaCoins.setTextColor(Color.WHITE)

                    }
                }


            }

            override fun afterTextChanged(s: Editable?) {
                println(s)
            }
        })
        Glide.with(getMainActivity()).load(coinInfoModel.image).into(coinImage)
        mainButton.text = "Sell Coin(S)"


        mainButton.setOnClickListener {
            if (editTextView.text.isEmpty()){
                Toast.makeText(activity,"Please Enter Number",Toast.LENGTH_SHORT).show()

            } else {
            val amountToSell = editTextView.text.toString().toIntOrNull()
            if (amountToSell != null && amountToSell > 0) {
            if (availableCoins < amountToSell) {
                Toast.makeText(getMainActivity(), "Not Enough Coins", Toast.LENGTH_SHORT).show()
            } else {
                DatabaseProvider().sellCoins(viewModel.currentUser.value!!,coinInfoModel,amountToSell!!,sellCoinsCallback)
                viewModel.updateNumberOfTransactions()

                viewModel.getUserData().let {
                    sellAdapter.setData(viewModel.currentUser.value?.coins?.values?.toTypedArray()!!)
                    sellAdapter.notifyDataSetChanged()
                }
                bottomSheetDialog.dismiss()

            }
         }
            }
        }
        xButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }
    @SuppressLint("SetTextI18n")
    fun showUsersMagmaCoins(amount:String) {
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_pop_up, null)

        val whiteText: TextView = dialogView.findViewById(R.id.whiteText)
        val xButton: Button = dialogView.findViewById(R.id.dismissButton)
        val descriptionGrayText: TextView = dialogView.findViewById(R.id.grayText)
        val mainButton: Button = dialogView.findViewById(R.id.applyButton)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        whiteText.text = "You currently have $amount magma coins"
        descriptionGrayText.isVisible = false
        mainButton.text = "OK"



        mainButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        xButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    fun showConsecutiveDayLogin(descriptionText:String,consecutiveDay:Int) {
        val bottomSheetDialog = BottomSheetDialog(getMainActivity())
        val dialogView = layoutInflater.inflate(R.layout.fragment_consecutive_day_pop_up, null)

        val whiteText: TextView = dialogView.findViewById(R.id.whiteText)
        val xButton: Button = dialogView.findViewById(R.id.dismissButton)
        val descriptionGrayText: TextView = dialogView.findViewById(R.id.grayText)
        val mainButton: Button = dialogView.findViewById(R.id.applyButton)
        val recyclerView:RecyclerView = dialogView.findViewById(R.id.dayRV)
        val adapter = ConsecutiveDaysAdapter(recyclerView,consecutiveDay)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        whiteText.text = "You started your trade coach streak!"
        descriptionGrayText.text = descriptionText

        Utils.setRecyclerHorizontal(recyclerView,adapter)
        adapter.scrollToConsecutiveDay()
        mainButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        xButton.setOnClickListener {
            viewModel.giveReward(3.00)
            bottomSheetDialog.dismiss()
        }
    }

}