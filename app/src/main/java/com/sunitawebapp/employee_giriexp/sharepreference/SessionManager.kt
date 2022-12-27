package com.sunitawebapp.admin_giriexp.sharepreference

import android.content.Context
import android.content.SharedPreferences

class SessionManager(  var context: Context) {
    var praference: SharedPreferences
    var editor: SharedPreferences.Editor


    init {

        praference = context.getSharedPreferences("SessionManager", 0)
        editor = praference.edit()
    }

    fun setusertypeid(usertypeid: Int) {
        editor.putInt("usertypeid", usertypeid)
        editor.apply()
    }

    fun getusertypeid(): Int {
        return praference.getInt("usertypeid", 0)
    }

    fun setuserloginid(userloginid: Int) {
        editor.putInt("userloginid", userloginid)
        editor.apply()
    }

    fun getuserloginid(): Int {
        return praference.getInt("userloginid", 0)
    }

    fun setusername(username: String) {
        editor.putString("username", username)
        editor.apply()
    }

    fun getusername(): String? {
        return praference.getString("username", "")
    }

    fun setemail(email: String) {
        editor.putString("email", email)
        editor.apply()
    }

    fun getemail(): String? {
        return praference.getString("email", "")
    }

    fun setLogged(logged: Boolean) {
        editor.putBoolean("logged", logged)
        editor.apply()
    }

    fun isLogged(): Boolean {
        return praference.getBoolean("logged",false)
    }

    fun setPriviouslivelocationPoint(PriviouslivelocationPoint: String) {
        editor.putString("PriviouslivelocationPoint", PriviouslivelocationPoint)
        editor.apply()
    }

    fun getPriviouslivelocationPoint(): String? {
        return praference.getString("PriviouslivelocationPoint", "")
    }
}
