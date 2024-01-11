package com.magma.tradecoach.ui.segmentPurchase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magma.tradecoach.R
import com.magma.tradecoach.model.PurchaseModel
import com.magma.tradecoach.model.TopicModel
import com.magma.tradecoach.utilities.Constants

class PurchaseAdapter  (
    var items :ArrayList<PurchaseModel>
) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.name.text = items[position].text
//        Glide.with(holder.itemView.context).load(items[position].image).into(holder.image)

    }

    override fun getItemCount(): Int {
        return Constants.PURCHASE_ITEM_COUNT
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val name: TextView = itemView.findViewById(R.id.name_text_view)
        val image: ImageView = itemView.findViewById(R.id.name_image_view)

    }

    fun setData(data: ArrayList<PurchaseModel>) {
        items = data
        notifyDataSetChanged()
    }

}