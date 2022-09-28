package com.sunitawebapp.admin_giriexp.retrofit

import com.sunitawebapp.employee_giriexp.retrofit.Models.Request.RegistrationReq
import com.sunitawebapp.employee_giriexp.retrofit.Models.Response.AppVersionAvailRes
import com.sunitawebapp.employee_giriexp.retrofit.Models.Response.RegisterRes
import com.sunitawebapp.employee_giriexp.retrofit.Models.Response.StationListRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("isUpdateAvailable")
    suspend fun isUpdateAvailable(@Query("configCode") configCode : String, @Query("appVersion") appVersion : String) : Response<AppVersionAvailRes>

    @POST("employeeRegistration")
    suspend fun isRegistration(@Body registrationReq : RegistrationReq) : Response<RegisterRes>

    @GET("getStationList")
    suspend fun getStationList() : Response<StationListRes>
}
