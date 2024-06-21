package com.magma.tradecoach.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.magma.tradecoach.R
import io.ak1.BubbleTabBar

fun FragmentActivity.setFragment(fragment: Fragment, position: Int) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragmentContainer, fragment)
    transaction.addToBackStack(null)
    transaction.commit()

    val tabLayout = findViewById<BubbleTabBar>(R.id.bubbleTabBar)
    tabLayout.setSelected(position)
}
fun Context.startActivityWithExtras(destination: Class<*>, extras: Bundle? = null) {
    val intent = Intent(this, destination)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}