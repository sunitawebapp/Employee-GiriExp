package com.sunitawebapp.employee_giriexp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.Models.Response.StationListRes
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StationListViewModel : ViewModel() {
    var StationListLivedata : MutableLiveData<Resource<StationListRes>> = MutableLiveData()

    fun StationList(){
        GlobalScope.launch {
            apicallForStationList()
        }
    }

    suspend   fun apicallForStationList(){
      //  StationListLivedata.postValue(Resource.Loading())
        var res= Repository.StationList()
        res.body()?.let {
            StationListLivedata.postValue(Resource.Success(it))
        }

    }

}
