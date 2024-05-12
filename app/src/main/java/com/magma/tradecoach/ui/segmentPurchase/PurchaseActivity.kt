package com.magma.tradecoach.ui.segmentPurchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.billingclient.api.Purchase
import com.magma.tradecoach.databinding.ActivityPuchaseBinding
import com.magma.tradecoach.interfaces.PurchaseAdapterInterface
import com.magma.tradecoach.utilities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchaseActivity:AppCompatActivity(),PurchaseAdapterInterface {
    private lateinit var binding: ActivityPuchaseBinding
    private lateinit var adapter:PurchaseAdapter
    private var billingManager: BillingManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPuchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        adapter = PurchaseAdapter(Constants.PURCHASE_OPTIONS,this)
        binding.purchaseRecyclerView.adapter = adapter
        binding.purchaseRecyclerView.layoutManager = GridLayoutManager(this,Constants.PURCHASE_GRID_COUNT)
        billingManager = BillingManager(this, this, object : BillingListener {
            override fun onPurchaseAcknowledged(purchase: Purchase) {
                println("done")
            }

            override fun onPurchaseFailed() {
                println("dnot done")

            }
        })
        billingManager?.startConnection()
    }

    override fun positionClicked(position: Int) {
        println("click")
        CoroutineScope(Dispatchers.Default).launch {
            billingManager?.purchaseProduct(Constants.PURCHASE_OPTIONS_IDS[position],SessionManager.getId())

        }


    }
    override fun onDestroy() {
        super.onDestroy()
        billingManager?.endConnection()
    }

    override fun onResume() {
        super.onResume()
        handlePendingPurchase()
    }
    private fun handlePendingPurchase() {
        try {
            CoroutineScope(Dispatchers.Default).launch {
                delay(2000L)
                billingManager?.handlePendingPayments()
            }
        } catch (e: Exception) {
            println(e)
        }
    }
}