package com.sunitawebapp.employee_giriexp.features.dashboard.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceReq
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceResp

import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.manager_giriexp.features.dashboard.models.UserInfoRes
import kotlinx.coroutines.launch
import retrofit2.Response

class GetUserInfoViewModel(application: Application) : AndroidViewModel(application) {
    var userInfoLiveData: MutableLiveData<Resource<UserInfoRes>> = MutableLiveData()

    fun userInfo(tblsysuserlogin_id: Int) {
        viewModelScope.launch {
            getUserInfo(tblsysuserlogin_id)
        }
    }

    private suspend fun getUserInfo(tblsysuserlogin_id: Int) {
        userInfoLiveData.postValue(Resource.Loading())
        val res = Repository.getUserInfo(tblsysuserlogin_id)
        userInfoLiveData.postValue(handlePostAttendance(res))
    }

    private fun handlePostAttendance(res: Response<UserInfoRes>): Resource<UserInfoRes> {
        if (res.isSuccessful) {
            res.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(res.message())
    }
}
