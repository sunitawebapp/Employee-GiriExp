package com.sunitawebapp.employee_giriexp.features.dashboard.models

data class PostAttendanceReq(
    val inLat: String,
    val inLocation: String,
    val inLong: String,
    val inorout: String,
    val outLat: String,
    val outLocation: String,
    val outLong: String,
    val tblsysuserlogin_id: Int
)
