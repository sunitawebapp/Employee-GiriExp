package com.sunitawebapp.employee_giriexp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.adapter.AttendanceAdapter
import com.sunitawebapp.employee_giriexp.adapter.MyLeaveAdapter
import com.sunitawebapp.employee_giriexp.databinding.FragmentAttendanceBinding
import com.sunitawebapp.employee_giriexp.databinding.FragmentMyLeavesListBinding
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.retrofit.model.response.AttendanceReportResItem
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LeaveReportResItem
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.AttendanceViewModel
import com.sunitawebapp.employee_giriexp.viewmodels.MyLeaveViewModel


class MyLeavesListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding : FragmentMyLeavesListBinding
    val myLeaveViewModel : MyLeaveViewModel by viewModels()

    var leaveReportResItem : MutableList<LeaveReportResItem> = ArrayList()
    var myLeaveAdapter = MyLeaveAdapter(leaveReportResItem)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyLeavesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myLeaveViewModel.leaveApplyReport("SELF", SessionManager(requireContext()).getuserloginid(),"")

       myLeaveViewModel.leaveReportLiveData.observe(viewLifecycleOwner){
           when (it) {
               is Resource.Success -> {
                   MyDialog.stopLoading()
                   it.data?.let { res ->
                       leaveReportResItem.addAll(it.data)
                       binding.rvLeaveList.adapter = myLeaveAdapter

                   }
               }
               is Resource.Error -> {
                   MyDialog.stopLoading()
                   it.message?.let { message ->
                       //  binding.root.showSnack("An error occurred: $message")
                   }
               }
               is Resource.Loading -> {
                   activity?.let {
                       MyDialog.showLoading(it)
                   }
               }
           }
       }
    }
}
