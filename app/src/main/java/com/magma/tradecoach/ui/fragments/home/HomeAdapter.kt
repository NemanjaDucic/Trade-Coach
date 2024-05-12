package com.magma.tradecoach.ui.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import com.magma.tradecoach.model.HomeItemModel

class HomeAdapter(
    var items: ArrayList<HomeItemModel>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            symbol.text = items[position].symbol
            name.text = items[position].name
            value.text = items[position].value
            lastValue.text = items[position].update
        }

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val symbol: TextView = itemView.findViewById(R.id.shortNameTV)
        val name: TextView = itemView.findViewById(R.id.fullnameTV)
        val value: TextView = itemView.findViewById(R.id.valueTV)
        val lastValue:TextView = itemView.findViewById(R.id.lastvalueTV)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<HomeItemModel>) {
        items = data
        notifyDataSetChanged()
    }
}