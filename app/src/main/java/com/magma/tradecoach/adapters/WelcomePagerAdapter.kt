
package com.magma.tradecoach.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.magma.tradecoach.R


class WelcomePagerAdapter(private val context: Context) : PagerAdapter() {

    private val gifs = intArrayOf(R.drawable.onboard1_anim, R.drawable.onboard2_anim, R.drawable.onboard3_anim)

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)

        val view: View = inflater.inflate(R.layout.item_welcome, null)
        val titleText:TextView = view.findViewById(R.id.tCaptionWelcome)
        val image:ImageView = view.findViewById(R.id.imageWelcome)


        Glide.with(context)
            .asGif()
            .load(gifs[position])
            .into(image)

        titleText.setTextColor(if (position == 0) Color.WHITE else ContextCompat.getColor(context, R.color.gradiantBottom))
        val viewPager: ViewPager = container as ViewPager



        viewPager.addView(view, 0)
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }
}

