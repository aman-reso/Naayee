package com.naayee.naayeeclient.codes.model
import com.google.gson.annotations.SerializedName

data class ShopProfileModel(
    var Message:String,
    var Status:Int,
    @SerializedName("EntityModel") var entityModel:PrEntityModel?,
    var Ratings: Int,
)

data class PrEntityModel(
    var Id: Int,
    var EntityName: String,
    var ContactName: String,
    var EmailId: String,
    var ContactNo: String,
    var Address: String,
    var StateId: Int,
    var StateName: String,
    var City: String,
    var PINCode: String,
    var LandMark: String,
    var OpeningTime: String,
    var ClosingTime: String,
    var Lattitude: String,
    var Longitude: String,
    var BannerPath: String
)


