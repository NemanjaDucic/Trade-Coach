package com.magma.tradecoach.utilities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.Intent
import android.os.*
import android.os.storage.StorageManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Utils(private val context: Context) {
    private var progressDialog: ProgressDialog? = null

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
    fun getTVText(textView: TextView): String {
        return textView.text.toString().trim { it <= ' ' }
    }

    fun intent(c: Class<*>, bundle: Bundle?) {
        val intent = Intent(context, c)
        bundle?.let { intent.putExtras(it) }
        context.startActivity(intent)
    }

    fun showLoadingDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    @SuppressLint("ServiceCast")
    fun checkStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageStatsManager = context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            if (storageManager == null || storageStatsManager == null) {
                return
            }
            val storageVolumes = storageManager.storageVolumes
            for (storageVolume in storageVolumes) {
                try {
                    val uuidStr = storageVolume.uuid
                    val uuid = uuidStr?.let { UUID.fromString(it) } ?: StorageManager.UUID_DEFAULT
                    val free = storageStatsManager.getFreeBytes(uuid)
                    val total = storageStatsManager.getTotalBytes(uuid)
                    val per = free.toDouble() / total * 100
                    if (per <= 15) {
                        showWarningDialog()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            val internalStatFs = StatFs(Environment.getRootDirectory().path)
            val internalTotal = internalStatFs.blockCountLong * internalStatFs.blockSizeLong / (KILOBYTE * KILOBYTE)
            val internalFree = internalStatFs.availableBlocksLong * internalStatFs.blockSizeLong / (KILOBYTE * KILOBYTE)
            val externalStatFs = StatFs(Environment.getExternalStorageDirectory().path)
            val externalTotal = externalStatFs.blockCountLong * externalStatFs.blockSizeLong / (KILOBYTE * KILOBYTE)
            val externalFree = externalStatFs.availableBlocksLong * externalStatFs.blockSizeLong / (KILOBYTE * KILOBYTE)
            val total = internalTotal + externalTotal
            val free = internalFree + externalFree
            val per = free / total * 100
            if (per <= 15) {
                showWarningDialog()
            }
        }
        startCheckStorage()
    }

    private fun startCheckStorage() {
        Handler().postDelayed({ checkStorage() }, 1000 * 60 * 60)
    }

    private var alertDialog: AlertDialog? = null

    private fun showWarningDialog() {
        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(context)
                .setTitle("WARNING")
                .setMessage(
                    "Less than 15% storage available on the device. \n" +
                            "Please go to the \"Settings\" menu on the login screen and press \"Optimize storage\".\n" +
                            "Contact the SENSE GARDEN administrator if the problem persists."
                )
                .setPositiveButton(android.R.string.ok, null)
                .show()
        } else {
            if (alertDialog?.isShowing == true) {
                alertDialog?.dismiss()
            }
            alertDialog?.show()
        }
    }

    fun dismissDialog() {
        progressDialog?.dismiss()
    }

    fun isEmailValid(editText: EditText): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(getETText(editText)).matches()
    }

    fun displayToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val KILOBYTE = 1024
    }
}
