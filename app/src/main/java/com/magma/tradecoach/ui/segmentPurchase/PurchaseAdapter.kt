package com.magma.tradecoach.ui.segmentPurchase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magma.tradecoach.R
import com.magma.tradecoach.interfaces.PurchaseAdapterInterface
import com.magma.tradecoach.model.PurchaseModel
import com.magma.tradecoach.utilities.Constants

class PurchaseAdapter  (
    var items :ArrayList<PurchaseModel>,
    var listener:PurchaseAdapterInterface
) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            name.text = items[position].text
            Glide.with(itemView.context).load(items[position].image).into(image)
            button.setOnClickListener {
                listener.positionClicked(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return Constants.PURCHASE_ITEM_COUNT
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name_text_view)
        val image: ImageView = itemView.findViewById(R.id.name_image_view)
        val button:Button = itemView.findViewById(R.id.purchaseButton)

    }


}