package com.magma.tradecoach.ui.fragments.currencies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magma.tradecoach.R
import com.magma.tradecoach.interfaces.CurrencyListener
import com.magma.tradecoach.model.MarketCoinModel

class CurrenciesAdapter (
    private var items :ArrayList<MarketCoinModel>,
    val listener:CurrencyListener
) : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_currencies, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.leftTextView.text = items[position].name
        holder.itemView.setOnClickListener {
            listener.getCoin(items[position])
        }
        if (items[position].marketCapChangePercentage24h > 0){
            Glide.with(holder.itemView.context).load(R.drawable.stock_up).placeholder(R.drawable.mcoin).into(holder.mImage)
        } else {
            Glide.with(holder.itemView.context).load(R.drawable.stock_dw).placeholder(R.drawable.mcoin).into(holder.mImage)

        }
        Glide.with(holder.itemView.context).load(items[position].image).placeholder(R.drawable.mcoin).into(holder.cImage)
        holder.rightTextView.text = items[position].currentPrice.toString()
        holder.rightTextViewBottom.text = "Last 24h >" + items[position].marketCapChangePercentage24h.toString()
        holder.leftTextViewBottom.text =  items[position].symbol

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val cImage: ImageView = itemView.findViewById(R.id.leftImageView)
        val leftTextView: TextView = itemView.findViewById(R.id.leftTV)
        val rightTextView: TextView = itemView.findViewById(R.id.rightTV)
        val rightTextViewBottom: TextView = itemView.findViewById(R.id.rightTVbottom)
        val leftTextViewBottom: TextView = itemView.findViewById(R.id.leftTVbottom)

        val mImage: ImageView = itemView.findViewById(R.id.rightImageView)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<MarketCoinModel>) {
        items = data
        notifyDataSetChanged()
    }
}