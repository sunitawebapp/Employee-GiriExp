package com.sunitawebapp.employee_giriexp.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sunitawebapp.admin_giriexp.sharepreference.SessionManager
import com.sunitawebapp.employee_giriexp.adapter.AttendanceAdapter
import com.sunitawebapp.employee_giriexp.databinding.FragmentAttendanceBinding
import com.sunitawebapp.employee_giriexp.ext.showSnack
import com.sunitawebapp.employee_giriexp.retrofit.Resource
import com.sunitawebapp.employee_giriexp.retrofit.model.response.AttendanceReportResItem
import com.sunitawebapp.employee_giriexp.utils.MyDialog
import com.sunitawebapp.employee_giriexp.viewmodels.AttendanceViewModel
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AttendanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AttendanceFragment : Fragment() , View.OnClickListener {
    lateinit var binding : FragmentAttendanceBinding
    val attendanceViewModel : AttendanceViewModel by viewModels()
    var attendanceReportResItem : MutableList<AttendanceReportResItem> = ArrayList()
    var attendanceAdapter = AttendanceAdapter(attendanceReportResItem)
    var FdateInt=0
    var TdateInt=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAttendanceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.apply {
            tvSelectedTDate.setOnClickListener(this@AttendanceFragment)
            tvSelectedFDate.setOnClickListener(this@AttendanceFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attendanceViewModel.AttendanceLivedata.observe(viewLifecycleOwner) {
            attendanceReportResItem.clear()
            when (it) {
                is Resource.Success -> {
                    MyDialog.stopLoading()
                    it.data?.let { res ->
                        attendanceReportResItem.addAll(it.data)
                        binding.rvAttendance.adapter = attendanceAdapter

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


        super.onViewCreated(view, savedInstanceState)
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
                        TdateInt =   Integer.parseInt("$year${String.format("%02d", newMonth)}${String.format("%02d", dayOfMonth)}")
                        if(TdateInt==0 && FdateInt==0 ){
                            binding.root.showSnack("Please select ")
                        }else {
                            attendanceViewModel.AttendanceList(
                                SessionManager(requireContext()).getuserloginid(),
                                SessionManager(requireContext()).getuserloginid(),
                                "single",
                                FdateInt.toString(),
                                TdateInt.toString()
                            )
                        }
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
                        FdateInt =   Integer.parseInt("$year${String.format("%02d", newMonth)}${String.format("%02d", dayOfMonth)}")
                        if(TdateInt==0 && FdateInt==0 ){
                            binding.root.showSnack("Please select ")
                        }else {
                            attendanceViewModel.AttendanceList(
                                SessionManager(requireContext()).getuserloginid(),
                                SessionManager(requireContext()).getuserloginid(),
                                "single",
                                FdateInt.toString(),
                                TdateInt.toString()
                            )
                        }
                    },
                    mYear,
                    mMonth,
                    mDay
                )

                //   datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog.show()
            }
        }
    }

}
