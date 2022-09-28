package com.sunitawebapp.employee_giriexp.retrofit.Models.Response

data class AppVersionAvailRes(
    val dbVersion: String,
    val error: Boolean,
    val errorMsg: String,
    val isUpdateAvailable: Boolean
)
