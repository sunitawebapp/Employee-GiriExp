package com.sunitawebapp.employee_giriexp.retrofit.model.response

data class LeaveReportResItem(
    val appliedByName: String,
    val appliedDate: String,
    val leaveBody: String,
    val leaveDateFrom: String,
    val leaveDatetoTo: String,
    val leaveSubject: String,
    val leaveType: String,
    val status: Int,
    val statusInWords: String,
    val statusRemarks: String,
    val statusUpdateDate: String,
    val status_tblsysuserlogin_id: Int,
    val tblleavedetails_id: Int,
    val tblleavetypemaster_id: Int,
    val tblsysuserlogin_id: Int
)
