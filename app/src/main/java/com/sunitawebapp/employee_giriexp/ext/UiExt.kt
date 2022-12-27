package com.sunitawebapp.employee_giriexp.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}
