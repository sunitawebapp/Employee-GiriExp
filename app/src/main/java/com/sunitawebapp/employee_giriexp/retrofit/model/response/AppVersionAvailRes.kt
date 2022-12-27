package com.sunitawebapp.employee_giriexp.retrofit.model.response

data class AppVersionAvailRes(
    val dbVersion: String,
    val error: Boolean,
    val errorMsg: String,
    val isUpdateAvailable: Boolean
)
