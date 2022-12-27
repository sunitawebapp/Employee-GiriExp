package com.sunitawebapp.employee_giriexp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunitawebapp.employee_giriexp.databinding.ItemAttendenceListBinding
import com.sunitawebapp.employee_giriexp.databinding.ItemLeaveListBinding
import com.sunitawebapp.employee_giriexp.retrofit.model.response.AttendanceReportResItem
import com.sunitawebapp.employee_giriexp.retrofit.model.response.LeaveReportResItem

class MyLeaveAdapter (var leaveReportResItem : MutableList<LeaveReportResItem>) : RecyclerView.Adapter<MyLeaveAdapter.MyLeaveViewHolder>() {

    class MyLeaveViewHolder(var binding: ItemLeaveListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setbinding(item : LeaveReportResItem){
            binding.leaveReportResItem=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLeaveViewHolder {
        return MyLeaveViewHolder( ItemLeaveListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyLeaveViewHolder, position: Int) {
        holder.setbinding(leaveReportResItem[position])
    }

    override fun getItemCount(): Int {
        return leaveReportResItem.size
    }
}
