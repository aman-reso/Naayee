package com.naayee.naayeeclient.codes.authConfig

import android.content.Context
import com.naayee.naayeeclient.codes.NaayeeApplication
import com.naayee.naayeeclient.codes.factory.ApiModule.Companion.SHARED_PREF

class AuthConfigManager {
    companion object {
        var tokenKey: String = "token";

        fun saveAuthToken(token: String?) {
            if (token != null) {
                val sharedPref = NaayeeApplication.getNaayeeAppContext()?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
                sharedPref?.edit()?.putString(tokenKey, token)?.apply()
            }
        }

        fun getAuthToken(): String {
//            val sharedPref = NaayeeApplication.getNaayeeAppContext()?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//            return sharedPref?.getString(tokenKey, "") ?: ""
            return "NvV9gzZ24oA="
        }

        fun logoutUser() {
            val sharedPref = NaayeeApplication.getNaayeeAppContext()?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
            sharedPref?.edit()?.putString(tokenKey, "")?.apply()
        }


    }
}