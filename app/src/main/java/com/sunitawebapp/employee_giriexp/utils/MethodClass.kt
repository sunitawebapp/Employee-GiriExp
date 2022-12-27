package com.sunitawebapp.employee_giriexp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.util.*

object MethodClass {
    fun openCalenderDialog(context: Context, tvDate: TextView) :String {
        var  date: String =""
        var mYear: Int = 0
        var mMonth: Int = 0
        var mDay: Int = 0
        // Get Current Date
        val c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        val datePickerDialog = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                tvDate.setText(String.format("%02d", dayOfMonth) + "/" +  String.format("%02d", monthOfYear + 1) + "/" + year)

            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
        //date= "$mYear"+"/"+mMonth +"/"+addZero(mDay)
        date= "$mYear"+"-"+String.format("%02d", mMonth+1) +"-"+String.format("%02d", mDay)
        return date
    }
}
