package com.sunitawebapp.employee_giriexp.retrofit.model.response

data class AttendanceReportResItem(
    val attDay: Int,
    val attMonth: Int,
    val attYear: Int,
    val attendanceDate: String,
    val employeeID: String,
    val inLat: String,
    val inLocation: String,
    val inLong: String,
    val inStatus: Int,
    val inTime: String,
    val name: String,
    val outLat: String,
    val outLocation: String,
    val outLong: String,
    val outStatus: Int,
    val outTime: String,
    val tblsysuserlogin_id: Int,
    val tbluserattendance_id: Int
)
