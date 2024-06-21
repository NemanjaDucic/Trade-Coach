package com.magma.tradecoach.ext

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

fun RecyclerView.setup(
        context: Context,
        adapter: RecyclerView.Adapter<*>,
        spanCount: Int = 1,
        hasFixedSize: Boolean = true
    ) {
        val layoutManager = GridLayoutManager(context, spanCount)
        this.setHasFixedSize(hasFixedSize)
        this.layoutManager = layoutManager
        this.adapter = adapter
}
fun RecyclerView.setHorizontalAdapter(context: Context, adapter: RecyclerView.Adapter<*>) {
    val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    this.setHasFixedSize(true)
    this.layoutManager = layoutManager
    this.adapter = adapter
}
fun View.animateAppear(duration: Long = 500) {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.duration = duration
    this.startAnimation(fadeIn)
    this.visibility = View.VISIBLE
}
fun View.animateFromBottom(duration: Long = 500) {
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = duration
    this.startAnimation(animate)
    this.visibility = View.VISIBLE
}

fun View.animateToBottom(duration: Long = 500) {
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.duration = duration
    this.startAnimation(animate)
    this.visibility = View.INVISIBLE
}
fun View.animateButtonClick(duration: Long = 300) {
    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1.0f, 0.9f, 1.0f)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.0f, 0.9f, 1.0f)
    val alpha = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0f, 0.7f, 1.0f)
    val animatorSet = AnimatorSet()
    animatorSet.playTogether(scaleX, scaleY, alpha)
    animatorSet.duration = duration
    animatorSet.interpolator = AccelerateDecelerateInterpolator()
    animatorSet.start()
}
fun EditText.isEmpty(): Boolean {
    return this.text.toString().trim { it <= ' ' }.isEmpty()
}

fun EditText.getTextTrimmed(): String {
    return this.text.toString().trim { it <= ' ' }
}
fun ImageView.setAvatarInitials(username: String) {
    val safeUsername = if (username.length > 2) username else "Tesla Coiner"
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.parseColor("#313131"))

    val paint = Paint()
    paint.textSize = 46f
    paint.color = Color.parseColor("#FFDC64")
    paint.isAntiAlias = true
    paint.textAlign = Paint.Align.CENTER

    val initials = safeUsername.substring(0, 2).uppercase(Locale.getDefault())
    val xPos = canvas.width / 2
    val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2)

    canvas.drawText(initials, xPos.toFloat(), yPos, paint)
    this.setImageBitmap(bitmap)
}