package com.magma.tradecoach.ui.fragments.community.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import com.magma.tradecoach.model.LeadrboardModel

class LeaderboardAdapter (
    var items :ArrayList<LeadrboardModel>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rank.text = items[position].rank.toString()
        holder.name.text = items[position].name
        holder.points.text = items[position].points.toString()

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val rank: TextView = itemView.findViewById(R.id.rankTV)
        val name: TextView = itemView.findViewById(R.id.nameTV)
        val points: TextView = itemView.findViewById(R.id.pointsTV)
    }

    fun setData(data: ArrayList<LeadrboardModel>) {
        items = data
        notifyDataSetChanged()
    }

}