package com.naayee.naayeeclient.codes.model
import com.google.gson.annotations.SerializedName

data class SubCategoryModel(
    var Message: String,
    var Status:Int,
    @SerializedName("Services") var serviceList: ArrayList<ServiceList>?
)

data class ServiceList(
    var Id: Int,var ServiceCode : String, var ServiceName : String, var Price : String
)