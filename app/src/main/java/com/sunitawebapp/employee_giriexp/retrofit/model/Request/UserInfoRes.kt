package com.sunitawebapp.manager_giriexp.features.dashboard.models

data class UserInfoRes(
    val inTime: String,
    val in_status: Int,
    val lat: String,
    val lng: String,
    val outTime: String,
    val out_status: Int,
    val radiusInMeters: Int,
    val tblsysuserlogin_id: Int
)