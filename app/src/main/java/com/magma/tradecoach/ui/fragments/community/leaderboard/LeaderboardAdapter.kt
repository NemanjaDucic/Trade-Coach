package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.annotation.SuppressLint
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
        holder.rank.text = rankPos[position]
        holder.name.text = items[position].username
        holder.points.text = getFormatedString(items[position].combinedValue)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rank: TextView = itemView.findViewById(R.id.rankTV)
        val name: TextView = itemView.findViewById(R.id.nameTV)
        val points: TextView = itemView.findViewById(R.id.pointsTV)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<UserWithCombinedValue>) {
        items = data
        notifyDataSetChanged()
    }
    private fun getFormatedString(number:Double):String{
        return String.format("%.2f", number)
    }
}