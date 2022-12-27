package com.sunitawebapp.employee_giriexp.retrofit.model.response

data class StationListResItem(
    val cityCode: String,
    val lat: String,
    val lng: String,
    val manager_tblsysuserlogin_id: Int,
    val radiusInMeters: Int,
    val tblmastcodecity_id: Int
)
