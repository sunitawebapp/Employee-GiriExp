package com.sunitawebapp.employee_giriexp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunitawebapp.employee_giriexp.databinding.ItemAttendenceListBinding
import com.sunitawebapp.employee_giriexp.retrofit.model.response.AttendanceReportResItem


class AttendanceAdapter(var attendanceReportResItem : MutableList<AttendanceReportResItem>) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    class AttendanceViewHolder(var binding: ItemAttendenceListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setbinding(item : AttendanceReportResItem){
            binding.attendanceReportRes=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        return AttendanceViewHolder( ItemAttendenceListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        holder.setbinding(attendanceReportResItem[position])
    }

    override fun getItemCount(): Int {
      return attendanceReportResItem.size
    }
}
