package com.sunitawebapp.employee_giriexp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunitawebapp.admin_giriexp.retrofit.repository.Repository
import com.sunitawebapp.employee_giriexp.retrofit.Models.Request.RegistrationReq
import com.sunitawebapp.employee_giriexp.retrofit.Models.Response.RegisterRes
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    var registerLivedata : MutableLiveData<Resource<RegisterRes>> = MutableLiveData()
    fun Registration(registrationReq : RegistrationReq){
        GlobalScope.launch {
            apicallforRegistration(registrationReq)
        }
    }

  suspend  fun apicallforRegistration(registrationReq : RegistrationReq){


      registerLivedata.postValue(Resource.Loading())
      var res=Repository.Registration(registrationReq)
      res.body()?.let {
          registerLivedata.postValue(Resource.Success(it))
      }
    }
}
