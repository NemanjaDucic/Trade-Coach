package com.magma.tradecoach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import kotlin.math.min


class ConsecutiveDaysAdapter(
    private val recyclerView: RecyclerView,
    private var consecutiveDay: Int
) : RecyclerView.Adapter<ConsecutiveDaysAdapter.ViewHolder>() {

    private val itemsPerPage = 7
    private var items = ArrayList<Int>((1..1000).toList())
    private var currentPage = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemIndex = position + currentPage * itemsPerPage
        if (itemIndex < items.size) {
            val currentItem = items[itemIndex]
            holder.day.text = currentItem.toString()

            if (currentItem == consecutiveDay) {
                holder.day.setBackgroundResource(R.drawable.date_dot)
            } else {
                holder.day.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun getItemCount(): Int {
        val totalItems = items.size
        val remainingItems = totalItems - currentPage * itemsPerPage
        return min(remainingItems, itemsPerPage)
    }

    fun scrollToConsecutiveDay() {
        val pageIndex = (consecutiveDay - 1) / itemsPerPage
        currentPage = pageIndex
        recyclerView.scrollToPosition(pageIndex * itemsPerPage)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.numberTV)
    }
}

