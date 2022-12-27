package com.sunitawebapp.employee_giriexp.receiver

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.sunitawebapp.employee_giriexp.MainActivity
import com.sunitawebapp.employee_giriexp.R


class NetworkChangeReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, p1: Intent?) {
        var status: String? = getConnectivityStatusString(context)
     var   dialog = Dialog(context)
        dialog.setContentView(R.layout.notavailable_internet_dialog)
        val restartapp: Button = dialog.findViewById(R.id.restartapp) as Button
      var  nettext = dialog.findViewById(R.id.nettext) as TextView

        restartapp.setOnClickListener(object : View.OnClickListener {
            override
            fun onClick(v: View?) {
                (context as Activity).finish()
                Log.d("clickedbutton", "yes")
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
            }
        })
        if(status!!.isEmpty()||status.equals("No internet is available")||status.equals("No Internet Connection")) {
            status="No Internet Connection";
            dialog.show();
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }

    companion object{
        fun getConnectivityStatusString(context: Context?): String? {
            var status: String? = null
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    status = "Wifi enabled"
                    return status
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    status = "Mobile data enabled"
                    return status
                }
            } else {
                status = "No internet is available"
                return status
            }
            return status
        }
    }
}
