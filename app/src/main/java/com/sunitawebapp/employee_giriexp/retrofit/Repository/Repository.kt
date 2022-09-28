package com.sunitawebapp.admin_giriexp.retrofit.repository

import com.sunitawebapp.admin_giriexp.retrofit.ApiClient
import com.sunitawebapp.admin_giriexp.retrofit.ApiInterface
import com.sunitawebapp.employee_giriexp.retrofit.Models.Request.RegistrationReq


object Repository {
 /*   suspend fun login(loginRequest: LoginReq) =
        ApiClient.createService(ApiInterface::class.java).isuserLogin(loginRequest)

        suspend fun versionAvailable() =
            ApiClient.createService(ApiInterface::class.java).isUpdateAvailable(Constents.configCode, BuildConfig.VERSION_NAME)

    suspend fun UserCount() =
        ApiClient.createService(ApiInterface::class.java).getUserCount()*/

    suspend fun Registration(registrationReq : RegistrationReq) =
        ApiClient.createService(ApiInterface::class.java).isRegistration(registrationReq)

    suspend fun StationList() =
        ApiClient.createService(ApiInterface::class.java).getStationList()
}
