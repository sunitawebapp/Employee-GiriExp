package com.sunitawebapp.employee_giriexp.retrofit.model.Request

data class LeaveApplyReq(
    val leavebody: String,
    val leavedate_from: String,
    val leavedateto_to: String,
    val leavesubject: String,
    val tblleavetypemaster_id: Int,
    val tblsysuserlogin_id: Int
)
