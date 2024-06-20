package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import com.magma.tradecoach.model.UserWithCombinedValue

class LeaderboardAdapter (
    var items :ArrayList<UserWithCombinedValue>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    var rankPos = arrayListOf("1","2","3")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("LeaderboardAdapter", "Binding item at position $position: ${items[position].username}")

        holder.rank.text = rankPos[position]
        holder.name.text = items[position].username
        holder.points.text = getFormatedString(items[position].combinedValue)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rank: TextView = itemView.findViewById(R.id.rankTV)
        val name: TextView = itemView.findViewById(R.id.nameTV)
        val points: TextView = itemView.findViewById(R.id.pointsTV)
    }


    fun setData(data: ArrayList<UserWithCombinedValue>) {
        Log.d("LeaderboardAdapter", "Setting data: ${data.size} items")

        items = data
        notifyDataSetChanged()
    }
    private fun getFormatedString(number:Double):String{
        return String.format("%.2f", number)
    }
}