package com.sunitawebapp.employee_giriexp.retrofit.Models.Request

data class RegistrationReq(
    val email: String,
    val fullName: String,
    val manager_tblsysuserlogin_id: Int,
    val mobile: String,
    val password: String,
    val tblmastcodecity_id: Int,
    val tblmastusertype_id: Int
)
