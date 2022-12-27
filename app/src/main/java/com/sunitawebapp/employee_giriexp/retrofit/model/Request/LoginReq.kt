package com.sunitawebapp.employee_giriexp.retrofit.model.Request

data class LoginReq(
    val password: String,
    val userName: String,
    val userTypeID: Int
)
