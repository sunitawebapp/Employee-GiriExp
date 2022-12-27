package com.sunitawebapp.employee_giriexp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.Resource

import com.sunitawebapp.employee_giriexp.retrofit.model.response.AttendanceReportRes


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AttendanceViewModel : ViewModel() {
    var AttendanceLivedata : MutableLiveData<Resource<AttendanceReportRes>> = MutableLiveData()
    fun AttendanceList(LoggedInUser : Int ,AttToShow : Int ,reqType : String ,fromDate : String ,toDate : String ){
        GlobalScope.launch {
            apicallForAttendanceList(LoggedInUser,AttToShow,reqType,fromDate,toDate)
        }
    }

    suspend   fun apicallForAttendanceList(LoggedInUser : Int ,AttToShow : Int ,reqType : String ,fromDate : String ,toDate : String){
        AttendanceLivedata.postValue(Resource.Loading())
        var res= Repository.AttendanceList(LoggedInUser,AttToShow,reqType,fromDate,toDate)
        res.body()?.let {

            AttendanceLivedata.postValue(Resource.Success(it))
        }

    }
}
