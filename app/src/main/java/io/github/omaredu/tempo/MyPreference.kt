package io.github.omaredu.tempo

import android.content.Context


class MyPreference(context : Context) {
    val PREFERENCE_NAME = "SharesPreferenceExaple"
    var PREFERENCE_LOGIN_COUNT = "LoginCount"
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLoginCount(): Int {
        return preference.getInt(PREFERENCE_LOGIN_COUNT, 0)
    }
    fun setLoginCount(count : Int){
        val editor = preference.edit()
        editor.putInt(PREFERENCE_LOGIN_COUNT,count)
        editor.apply()
    }
}