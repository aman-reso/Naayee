package com.naayee.naayeeclient.codes.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.naayee.naayeeclient.codes.model.ServiceModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage @Inject constructor() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    fun storeSelectedServices(servicesList: List<ServiceModel>?) {
        if (servicesList == null) {
            return
        }
        try {
            sharedPreferences.edit().putString(KEY_SAVE_SERVICE, gson.toJson(servicesList)).apply()
        } catch (e: Exception) {
            return
        }
    }

    fun getSelectedServices(): List<ServiceModel?>? {
        val json: String? = sharedPreferences.getString(KEY_SAVE_SERVICE, null)
        val type = object : TypeToken<ArrayList<ServiceModel?>?>() {}.type
        return gson.fromJson<ArrayList<ServiceModel?>>(json, type)
    }

    companion object {
        const val KEY_SAVE_SERVICE = "selected_service"
    }

}