package com.magma.tradecoach.utilities

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.*
import android.view.View
import android.view.animation.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.magma.tradecoach.R
import com.magma.tradecoach.model.UserDataModel
import io.ak1.BubbleTabBar
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
object Utils {
    private var context: Context? = null
    private var progressDialog: ProgressDialog? = null
    var didShowLogin = false
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun isETEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    fun getETText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    fun intent(context: Context, c: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, c)
        if (bundle != null) intent.putExtras(bundle)
        context.startActivity(intent)
    }

    fun intent(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }

    fun intent(c: Class<*>, bundle: Bundle?) {
        val intent = Intent(context, c)
        bundle?.let { intent.putExtras(it) }
        context?.startActivity(intent)
    }

    fun getTVText(textView: TextView): String {
        return textView.text.toString().trim { it <= ' ' }
    }

    fun showLoadingDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun dismissDialog() {
        progressDialog?.dismiss()
    }

    fun setContext(context: Context) {
        this.context = context.applicationContext
    }

    fun isEmailValid(editText: EditText): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(getETText(editText)).matches()
    }

    fun displayToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
    fun setFragment(activity: FragmentActivity, fragment: Fragment, position: Int) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        val tabLayout = activity.findViewById<BubbleTabBar>(R.id.bubbleTabBar)
        tabLayout.setSelected(position)
    }
    fun setRecycler(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = GridLayoutManager(context, 1)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }
     fun setAvatarInitials(username: String, imageView: ImageView) {
        val safeUsername = if (username.length > 2) username else "Tesla Coiner"
        val b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.drawColor(Color.parseColor("#313131"))
        val paint = Paint()
        paint.textSize = 46f
        paint.color = Color.parseColor("#FFDC64")
        c.drawText(safeUsername.substring(0, 2).uppercase(Locale.getDefault()), 22f, 65f, paint)
        imageView.setImageBitmap(b)
    }

    fun isPremiumUser(user:UserDataModel):Boolean{
        return user.isPremium == true
    }

     fun splitTime(time: String): String {
        return try {
            val split = time.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            split[1]
        } catch (e: Exception) {
            " "
        }
    }
    fun setRecyclerHorizontal(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    fun setRecyclerTwoSpan(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = GridLayoutManager(context, 7)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    fun setRecyclerThreeSpan(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = GridLayoutManager(context, 3)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    fun isValidTime(time: String): Boolean {
        val split = time.split(":").toTypedArray()
        val hours = split[0].toInt()
        val minutes = split[1].toInt()
        val seconds = split[2].toInt()
        return minutes >= 0 && minutes < 60 && seconds >= 0 && seconds < 60 && hours >= 0
    }

    fun isValidDate(date: String): Boolean {
        if (date.isEmpty()) return false
        val birthday = date.split("/").toTypedArray()
        val year = birthday[2].toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return year < currentYear && year > 1900
    }
     fun animateViewFromBottom(view: View) {
        val animate = TranslateAnimation(0f, 0f, view.height.toFloat(), 0f)
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.VISIBLE
    }
     fun animateViewAppear(view: View) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 500
        view.startAnimation(fadeIn)
        view.visibility = View.VISIBLE
    }
    fun animateViewToBottom(view: View) {
        val animate = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
        animate.duration = 500
        view.startAnimation(animate)
        view.visibility = View.INVISIBLE
    }
     fun startPulseAnimation(thisView: View) {
         val pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(
             thisView,
             PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f, 0.8f),
             PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f, 0.8f)
         )

         pulseAnimator.duration = 1000 // Set the total duration of the animation
         pulseAnimator.repeatCount = ValueAnimator.INFINITE // Infinite repetition
         pulseAnimator.repeatMode = ValueAnimator.REVERSE // Reverse the animation on each repetition
         pulseAnimator.interpolator = AccelerateDecelerateInterpolator()

         pulseAnimator.start()
     }

     fun animateButtonClick(view:View) {
        // Scale animation
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.0f, 0.9f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.0f, 0.9f, 1.0f)

        // Alpha (opacity) animation
        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.7f, 1.0f)

        // Combine animations into a set
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, alpha)
        animatorSet.duration = 300 // Set the total duration of the animation
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        // Start the animation
        animatorSet.start()
    }
    private const val KILOBYTE = 1024
    fun updateFirebaseUserData(userId: String, consecutiveDays: Int) {
        val databaseRef = FirebaseDatabase.getInstance().reference

        databaseRef.child("users").child(userId).child("streak").setValue(consecutiveDays)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    println("Firebase" +  "Error updating streak in Firebase")
                }
            }
    }
    fun animateClick(view: View, scaleFactor: Float, duration: Long) {
        // Define the initial and final scale factors
        val initialScaleX = view.scaleX
        val initialScaleY = view.scaleY
        val finalScaleX = initialScaleX * scaleFactor
        val finalScaleY = initialScaleY * scaleFactor

        // Animate the view to increase its size
        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, initialScaleX, finalScaleX)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, initialScaleY, finalScaleY)

        // Combine the animations
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
        animatorSet.duration = duration
        animatorSet.start()

        // Reverse the animation to return to the original size
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(p0: android.animation.Animator) {
                val reverseScaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, finalScaleX, initialScaleX)
                val reverseScaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, finalScaleY, initialScaleY)
                val reverseAnimatorSet = AnimatorSet()
                reverseAnimatorSet.playTogether(reverseScaleXAnimator, reverseScaleYAnimator)
                reverseAnimatorSet.duration = duration
                reverseAnimatorSet.start()
            }
        })
    }
}
