package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magma.tradecoach.R
import io.ak1.BubbleTabBar
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
object Utils {
    private var context: Context? = null
    private var progressDialog: ProgressDialog? = null
    private var alertDialog: AlertDialog? = null

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

    fun setRecyclerHorizontal(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }

    fun setRecyclerTwoSpan(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val layoutManager = GridLayoutManager(context, 2)
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

    private const val KILOBYTE = 1024
}
