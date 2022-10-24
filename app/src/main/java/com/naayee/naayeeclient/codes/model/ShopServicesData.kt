package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class ShopServicesData(
    var Message:String,
    var Status:Int,
    @SerializedName("ServiceModel") var serviceModel:ArrayList<ServiceModel>?,
)

data class ServiceModel(
    var Id: Int,
    var ServiceCode: String,
    var ServiceName: String,
    var Price: Double,
    var GST: Double,
    var Discount: Double,
    var CheckDiscount: Int,
    var ItemCount:Int=0
)
data class SelectedItem(var ServiceId:Int?=0,var ServiceQty:Int?=0)
