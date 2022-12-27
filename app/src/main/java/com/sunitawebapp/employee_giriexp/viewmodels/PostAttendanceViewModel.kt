package com.sunitawebapp.employee_giriexp.features.dashboard.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceReq
import com.sunitawebapp.employee_giriexp.features.dashboard.models.PostAttendanceResp

import com.sunitawebapp.employee_giriexp.retrofit.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PostAttendanceViewModel(application: Application) : AndroidViewModel(application) {
    var postAttLiveData: MutableLiveData<Resource<PostAttendanceResp>> = MutableLiveData()

    fun postAtt(postAttendanceReq: PostAttendanceReq) {
        viewModelScope.launch {
            postAttendance(postAttendanceReq)
        }
    }

    private suspend fun postAttendance(postAttendanceReq: PostAttendanceReq) {
        postAttLiveData.postValue(Resource.Loading())
        val res = Repository.postAttendance(postAttendanceReq)
        postAttLiveData.postValue(handlePostAttendance(res))
    }

    private fun handlePostAttendance(res: Response<PostAttendanceResp>): Resource<PostAttendanceResp> {
        if (res.isSuccessful) {
            res.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(res.message())
    }
}
