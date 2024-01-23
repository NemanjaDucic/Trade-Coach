package com.magma.tradecoach.utilities

import android.app.ProgressDialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.magma.tradecoach.ui.segmentMain.MainActivity

abstract class BaseFragment: Fragment() {
    lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        progressDialog = ProgressDialog(context, com.hbb20.R.style.AlertDialog_AppCompat)
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)
    }

    override fun getContext(): MainActivity? {
        return activity as MainActivity
    }
}