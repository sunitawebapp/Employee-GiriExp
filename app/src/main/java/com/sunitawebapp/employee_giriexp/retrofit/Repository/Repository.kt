package com.sunitawebapp.admin_giriexp.retrofit.repository

import com.sunitawebapp.admin_giriexp.retrofit.ApiClient
import com.sunitawebapp.admin_giriexp.retrofit.ApiInterface
import com.sunitawebapp.employee_giriexp.BuildConfig
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LeaveApplyReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LoginReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.RegistrationReq
import com.sunitawebapp.employee_giriexp.utils.Constents


object Repository {
 /*   suspend fun login(loginRequest: LoginReq) =
        ApiClient.createService(ApiInterface::class.java).isuserLogin(loginRequest)

        suspend fun versionAvailable() =
            ApiClient.createService(ApiInterface::class.java).isUpdateAvailable(Constents.configCode, BuildConfig.VERSION_NAME)

    suspend fun UserCount() =
        ApiClient.createService(ApiInterface::class.java).getUserCount()*/
 suspend fun login(loginRequest: LoginReq) =
     ApiClient.createService(ApiInterface::class.java).isuserLogin(loginRequest)

    suspend fun LeaveApply(leaveApplyReq: LeaveApplyReq) =
        ApiClient.createService(ApiInterface::class.java).LeaveApply(leaveApplyReq)

    suspend fun versionAvailable() =
     ApiClient.createService(ApiInterface::class.java).isUpdateAvailable(Constents.configCode, BuildConfig.VERSION_NAME)

    suspend fun Registration(registrationReq : RegistrationReq) =
        ApiClient.createService(ApiInterface::class.java).isRegistration(registrationReq)

    suspend fun StationList() =
        ApiClient.createService(ApiInterface::class.java).getStationList()

    suspend fun AttendanceList(LoggedInUser : Int ,AttToShow : Int ,reqType : String ,fromDate : String ,toDate : String) =
        ApiClient.createService(ApiInterface::class.java).getAttendanceList(LoggedInUser,AttToShow,reqType,fromDate,toDate)

    suspend fun getUserInfo(tblsysuserlogin_id: Int) =
        ApiClient.createService(ApiInterface::class.java).getUserInfo(tblsysuserlogin_id)

    suspend fun postAttendance(postAttendanceReq: PostAttendanceReq) =
        ApiClient.createService(ApiInterface::class.java).postAttendance(postAttendanceReq)

    suspend fun getLeaveApplyReport(reqType: String,tblsysuserlogin_id: Int,status: String) =
        ApiClient.createService(ApiInterface::class.java).getLeaveApplyReport(reqType,tblsysuserlogin_id,status)
}
