package com.sunitawebapp.employee_giriexp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.callbacks.OnDialogButtonClickListener


object MyDialog {
    private lateinit var dialog: Dialog

    fun showLoading(context: Context){
        dialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_loading,null)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setContentView(view)

            val layoutParams =  WindowManager.LayoutParams().apply {
                copyFrom(dialog.window?.attributes)
                width = WindowManager.LayoutParams.WRAP_CONTENT
            }

            dialog.show()
            dialog.window?.attributes = layoutParams
        }
    }

    fun stopLoading(){
        dialog.dismiss()
    }

    fun showConfirmation(
        title: String,
        msg: String,
        activity: Activity,
        whatFor: String,
        onDialogButtonClickListener: OnDialogButtonClickListener
    ) {
        activity.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    onDialogButtonClickListener.onPositiveButtonClicked(whatFor)
                }.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                    onDialogButtonClickListener.onNegativeButtonClicked(whatFor)
                }
                .show()
        }
    }
}
