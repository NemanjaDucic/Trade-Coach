package com.magma.tradecoach.ui.fragments.community.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import com.magma.tradecoach.model.TopicModel

class FeedAdapter  (
    var items :ArrayList<TopicModel>
) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.topic.text = items[position].title
        holder.description.text = items[position].subtitle

    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val topic: TextView = itemView.findViewById(R.id.textTitle)
        val description: TextView = itemView.findViewById(R.id.textTopic)
    }

    fun setData(data: ArrayList<TopicModel>) {
        items = data
        notifyDataSetChanged()
    }

}