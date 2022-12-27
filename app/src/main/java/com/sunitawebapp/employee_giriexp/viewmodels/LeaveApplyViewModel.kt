package com.sunitawebapp.employee_giriexp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LeaveApplyReq
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LoginReq
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LeaveRes
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LoginRes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LeaveApplyViewModel (application: Application) : AndroidViewModel(application) {
    var loginLiveData: MutableLiveData<Resource<LeaveRes>> = MutableLiveData()

    fun leaveApply(loginRequest: LeaveApplyReq) {
        GlobalScope.launch {
            loginReq(loginRequest)
        }
    }

    suspend fun loginReq(loginRequest: LeaveApplyReq) {
        loginLiveData.postValue(Resource.Loading())
        val loginRes = Repository.LeaveApply(loginRequest)
        loginLiveData.postValue(handleLoginRes(loginRes))
    }

    private fun handleLoginRes(loginRes: Response<LeaveRes>): Resource<LeaveRes> {
        if (loginRes.isSuccessful) {
            loginRes.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(loginRes.message())
    }
}
