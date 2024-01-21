package com.magma.tradecoach.ui.fragments.currencies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magma.tradecoach.R
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.utilities.Constants

class CurrenciesAdapter (
    private var items :ArrayList<MarketCoinModel>
) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {
    var kind = Constants.MAIN
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_currencies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (kind){
            Constants.MAIN -> {
                holder.mImage.isVisible = false
                    holder.rightTextView.isVisible = false
            }
            Constants.COMPARE -> {

            }
            Constants.GROWTH -> {

            }

        }
        holder.leftTextView.text = items[position].currentPrice.toString()
        Glide.with(holder.itemView.context).load(items[position].image).placeholder(R.drawable.mcoin).into(holder.cImage)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val cImage: ImageView = itemView.findViewById(R.id.leftImageView)
        val leftTextView: TextView = itemView.findViewById(R.id.leftTV)
        val rightTextView: TextView = itemView.findViewById(R.id.rightTV)
        val mImage: ImageView = itemView.findViewById(R.id.rightImageView)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<MarketCoinModel>) {
        items = data
        notifyDataSetChanged()
    }
}