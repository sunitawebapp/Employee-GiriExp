package com.sunitawebapp.employee_giriexp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LeaveReportRes
import com.sunitawebapp.manager_giriexp.features.dashboard.models.UserInfoRes
import kotlinx.coroutines.launch
import retrofit2.Response

class MyLeaveViewModel (application: Application) : AndroidViewModel(application) {
    var leaveReportLiveData: MutableLiveData<Resource<LeaveReportRes>> = MutableLiveData()

    fun leaveApplyReport(reqType: String,tblsysuserlogin_id: Int,status: String) {
        viewModelScope.launch {
            getLeaveApplyReport(reqType,tblsysuserlogin_id,status)
        }
    }

    private suspend fun getLeaveApplyReport(reqType: String,tblsysuserlogin_id: Int,status: String) {
        leaveReportLiveData.postValue(Resource.Loading())
        val res = Repository.getLeaveApplyReport(reqType,tblsysuserlogin_id,status)
        leaveReportLiveData.postValue(handlePostLeaveApplyReport(res))
    }

    private fun handlePostLeaveApplyReport(res: Response<LeaveReportRes>): Resource<LeaveReportRes> {
        if (res.isSuccessful) {
            res.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(res.message())
    }
}
