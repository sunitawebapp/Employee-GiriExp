package com.sunitawebapp.admin_giriexp.retrofit

import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceReq
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceResp
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LeaveApplyReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LoginReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.RegistrationReq
import com.sunitawebapp.employee_giriexp.retrofit.model.response.*
import com.sunitawebapp.manager_giriexp.features.dashboard.models.UserInfoRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("userLogin")
    suspend fun isuserLogin(@Body loginReq : LoginReq) : Response<LoginRes>

    @POST("PostApplyLeave")
    suspend fun LeaveApply(@Body leaveApplyReq : LeaveApplyReq) : Response<LeaveRes>

    @GET("isUpdateAvailable")
    suspend fun isUpdateAvailable(@Query("configCode") configCode : String, @Query("appVersion") appVersion : String) : Response<AppVersionAvailRes>

    @POST("employeeRegistration")
    suspend fun isRegistration(@Body registrationReq : RegistrationReq) : Response<RegisterRes>

    @GET("getStationList")
    suspend fun getStationList() : Response<StationListRes>

    @GET("getAttendanceReport")
    suspend fun getAttendanceList(
        @Query("tblsysuserlogin_idLoggedInUser") tblsysuserlogin_idLoggedInUser : Int,
        @Query("tblsysuserlogin_idAttToShow") tblsysuserlogin_idAttToShow : Int,
        @Query("reqType") reqType : String,
        @Query("fromDate") fromDate : String,
        @Query("toDate") toDate : String
    ) : Response<AttendanceReportRes>

    @POST("/postAttendance")
    suspend fun postAttendance(@Body postAttendanceReq: PostAttendanceReq): Response<PostAttendanceResp>

    @GET("/auspGetUserInfo")
    suspend fun getUserInfo(@Query("tblsysuserlogin_id") tblsysuserlogin_id: Int): Response<UserInfoRes>


    @GET("/GetEmployeeLeaveApplyReport")
    suspend fun getLeaveApplyReport(
        @Query("reqType") reqType: String,
        @Query("tblsysuserlogin_id") tblsysuserlogin_id: Int,
        @Query("status") status: String):
            Response<LeaveReportRes>
}
