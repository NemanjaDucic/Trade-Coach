package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.model.UserWithCombinedValue

class LeaderboardAdapter (
    var items :List<UserDataModel>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }
    var mode = "Login"
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("LeaderboardAdapter", "Binding item at position $position: ${items[position].username}")


        holder.rank.text = (position + 4).toString()
        holder.name.text = items[position].username
        if (mode == "Login"){
            holder.points.text = items[position].streak.toString()
        } else if (mode == "Ads"){
            holder.points.text = items[position].addsWatched.toString()

        } else {
            holder.points.text = items[position].transactionsCompleted.toString()

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val rank: TextView = itemView.findViewById(R.id.rankTV)
        val name: TextView = itemView.findViewById(R.id.nameTV)
        val points: TextView = itemView.findViewById(R.id.pointsTV)
    }


    fun setData(data: List<UserDataModel>) {
        Log.d("LeaderboardAdapter", "Setting data: ${data.size} items")

        items = data
        notifyDataSetChanged()
    }
    private fun getFormatedString(number:Double):String{
        return String.format("%.2f", number)
    }
}