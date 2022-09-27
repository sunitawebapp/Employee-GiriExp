package com.sunitawebapp.employee_giriexp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.sunitawebapp.employee_giriexp.R


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
}