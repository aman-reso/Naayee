package com.naayee.naayeeclient.codes.utility

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.naayee.naayeeclient.codes.model.SelectedItem
import com.naayee.naayeeclient.codes.model.ServiceModel
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Mapper @Inject constructor() {
    @Inject
    lateinit var gson: Gson
    fun convertServicesListToSelectedItemList(inputList: List<ServiceModel>?): ArrayList<SelectedItem>? {
        if (inputList == null) {
            return null
        }
        val list = ArrayList<SelectedItem>()
        inputList.forEach {
            val selectedItem = SelectedItem(it.Id, it.ItemCount)
            list.add(selectedItem)
        }
        return list
//        val listString = gson.toJson(list, object : TypeToken<ArrayList<ServiceModel>>() {}.type)
//        return JSONArray(listString)

    }
}