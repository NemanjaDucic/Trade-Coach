package com.magma.tradecoach.ui.segmentPurchase

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.magma.tradecoach.databinding.ActivityPuchaseBinding
import com.magma.tradecoach.utilities.Constants

class PurchaseActivity:AppCompatActivity() {
    private lateinit var binding: ActivityPuchaseBinding
    private lateinit var adapter:PurchaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPuchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        adapter = PurchaseAdapter(arrayListOf())
        binding.purchaseRecyclerView.adapter = adapter
        binding.purchaseRecyclerView.layoutManager = GridLayoutManager(this,Constants.PURCHASE_GRID_COUNT)
    }
}