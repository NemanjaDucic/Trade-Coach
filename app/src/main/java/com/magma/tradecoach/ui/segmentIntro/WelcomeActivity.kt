package com.magma.tradecoach.ui.segmentIntro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.magma.tradecoach.databinding.ActivityWelcomeBinding
import com.magma.tradecoach.model.WelcomePagerAdapter
import com.magma.tradecoach.utilities.Utils

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val adapter = WelcomePagerAdapter(this)
        binding.viewPagerWelcome.adapter = adapter
        binding.tabLayoutWelcome.setupWithViewPager(binding.viewPagerWelcome, true)

        binding.btNextWelcome.setOnClickListener {
            if (binding.viewPagerWelcome.currentItem == 2)
              Utils.intent(this, RegisterActivity::class.java,null)
            else binding.viewPagerWelcome.currentItem = binding.viewPagerWelcome.currentItem + 1
        }

        //Za sta nam ovo sluzi? Za neku buducu upotrebu ili?
        binding.viewPagerWelcome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {

            }
        })
    }
}