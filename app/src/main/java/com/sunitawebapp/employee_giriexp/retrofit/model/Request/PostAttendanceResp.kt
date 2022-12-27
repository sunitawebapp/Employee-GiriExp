package com.sunitawebapp.employee_giriexp.features.dashboard.models

data class PostAttendanceResp(
    val DB_MSG: String,
    val DB_STATUS: Boolean,
    val inTime: String,
    val outTime: String
)
