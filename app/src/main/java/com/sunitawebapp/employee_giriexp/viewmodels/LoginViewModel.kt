package com.sunitawebapp.employee_giriexp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LoginReq
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LoginRes
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var loginLiveData: MutableLiveData<Resource<LoginRes>> = MutableLiveData()

    fun login(loginRequest: LoginReq) {
        GlobalScope.launch {
            loginReq(loginRequest)
        }
    }

     suspend fun loginReq(loginRequest: LoginReq) {
        loginLiveData.postValue(Resource.Loading())
        val loginRes = Repository.login(loginRequest)
        loginLiveData.postValue(handleLoginRes(loginRes))
    }

    private fun handleLoginRes(loginRes: Response<LoginRes>): Resource<LoginRes> {
        if (loginRes.isSuccessful) {
            loginRes.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(loginRes.message())
    }
}
