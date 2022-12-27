package com.sunitawebapp.employee_giriexp.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.R
import com.sunitawebapp.employee_giriexp.databinding.FragmentApplyLeaveBinding
import com.sunitawebapp.employee_giriexp.databinding.FragmentMyLeavesListBinding
import com.sunitawebapp.employee_giriexp.ext.showSnack
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.retrofit.model.Request.LeaveApplyReq
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.LeaveApplyViewModel
import com.sunitawebapp.employee_giriexp.viewmodels.MyLeaveViewModel
import java.util.*

class ApplyLeaveFragment : Fragment() ,View.OnClickListener{
    lateinit var binding : FragmentApplyLeaveBinding
    val leaveApplyViewModel : LeaveApplyViewModel by viewModels()
    var FdateInt=""
    var TdateInt=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentApplyLeaveBinding.inflate(inflater, container, false)
        binding.apply {
            tvSelectedFDate.setOnClickListener(this@ApplyLeaveFragment)
            tvSelectedTDate.setOnClickListener(this@ApplyLeaveFragment)
            btnApply.setOnClickListener(this@ApplyLeaveFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leaveApplyViewModel.loginLiveData.observe(viewLifecycleOwner){res->
            when(res) {
                is Resource.Success -> {
                    res.data?.let {
                        MyDialog.stopLoading()
                        Toast.makeText(requireContext(), res.data.MSG, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.applyLeaveFragment)
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    MyDialog.showLoading(requireContext())
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.tvSelectedTDate->{
                val c = Calendar.getInstance();
                var  mYear = c.get(Calendar.YEAR);
                var  mMonth = c.get(Calendar.MONTH);
                var  mDay = c.get(Calendar.DAY_OF_MONTH);
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->

                        //  dateInt=   "$mYear"+""+ MethodClass.addZero(mMonth+1) +""+ MethodClass.addZero(mDay)

                        val newMonth = monthOfYear + 1
                        binding. tvSelectedTDate.setText("${String.format("%02d", dayOfMonth)} / ${String.format("%02d", newMonth)} / $year")
                        TdateInt =  "$year-${String.format("%02d", newMonth)}-${String.format("%02d", dayOfMonth)}"

                    },
                    mYear,
                    mMonth,
                    mDay
                )

                //   datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show()
            }
            binding.tvSelectedFDate->{
                val c = Calendar.getInstance();
                var  mYear = c.get(Calendar.YEAR);
                var  mMonth = c.get(Calendar.MONTH);
                var  mDay = c.get(Calendar.DAY_OF_MONTH);
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { view, year, monthOfYear, dayOfMonth ->

                        //  dateInt=   "$mYear"+""+ MethodClass.addZero(mMonth+1) +""+ MethodClass.addZero(mDay)

                        val newMonth = monthOfYear + 1
                        binding. tvSelectedFDate.setText("${String.format("%02d", dayOfMonth)} / ${String.format("%02d", newMonth)} / $year")
                        FdateInt =  "$year-${String.format("%02d", newMonth)}-${String.format("%02d", dayOfMonth)}"


                    },
                    mYear,
                    mMonth,
                    mDay
                )

                //   datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show()
            }
            binding.btnApply->{
                if (FdateInt .equals("")){
                    Toast.makeText(requireContext(),"Please select from date", Toast.LENGTH_SHORT).show()
                }else  if (TdateInt .equals("")){
                    Toast.makeText(requireContext(),"Please select to date", Toast.LENGTH_SHORT).show()
                }else  if (binding.etnLeaveSubject.text.toString().equals("")){
                    binding.etnLeaveSubject.setError("Please Enter Subject")
                }else  if (binding.etnReason.text.toString().equals("")){
                    binding.etnReason.setError("Please Enter Reason")
                }else{
                    leaveApplyViewModel.leaveApply(
                        LeaveApplyReq(
                            leavebody=binding.etnReason.text.toString(),
                            leavedate_from=FdateInt,
                            leavedateto_to=TdateInt,
                            leavesubject=binding.etnLeaveSubject.text.toString(),
                            tblleavetypemaster_id=2,
                            tblsysuserlogin_id=SessionManager(requireContext()).getuserloginid()
                        )
                    )
                }


            }
        }
    }
}
