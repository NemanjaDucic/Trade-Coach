package com.magma.tradecoach.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magma.tradecoach.R
import com.magma.tradecoach.interfaces.MyCoinClickedInterface
import com.magma.tradecoach.model.CoinModel

class MyPossessionsAdapter(var usersPossessions:Array<CoinModel>,val listener:MyCoinClickedInterface
) : RecyclerView.Adapter<MyPossessionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_possession, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
           Glide.with(itemView.context).load(usersPossessions[position].name?.image).into(leftImageView)
           Glide.with(itemView.context).load(R.drawable.mcoin).into(rightImageView)
           leftTextView.text = usersPossessions[position].quantity.toString()
           rightTextView.text = (usersPossessions[position].quantity?.times(usersPossessions[position].name!!.currentPrice)).toString()
           itemView.setOnClickListener {
               listener.coinClicked(usersPossessions[position])
           }
       }
    }

    override fun getItemCount(): Int {

        return usersPossessions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val leftImageView:ImageView = itemView.findViewById(R.id.coinIco)
        val rightImageView:ImageView = itemView.findViewById(R.id.coinIco2)
      val  leftTextView:TextView = itemView.findViewById(R.id.coinText)
        val  rightTextView:TextView = itemView.findViewById(R.id.coinText2)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data:Array<CoinModel>){
        usersPossessions = data
        notifyDataSetChanged()
    }

}