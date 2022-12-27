package com.sunitawebapp.employee_giriexp.utils

import android.app.Application
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager(this)
    }
}
