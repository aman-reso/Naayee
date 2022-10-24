package com.naayee.naayeeclient.codes.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RecommendationsModel(
    var Message: String,
    var Status:Int,
    @SerializedName("Entities") var entityList: ArrayList<EntityModel>?
)

data class EntityModel(
    var Id: Int,var EntityName : String?=null, var ContactName : String?=null, var EmailId : String?=null,var ContactNo: String?=null,
    var Address: String?=null, var StateId: Int,var StateName: String?=null, var City: String?=null, var PINCode: String?=null,
    var LandMark: String?=null, var OpeningTime: String?=null,var ClosingTime: String?=null, var Lattitude: String?=null, var Longitude: String?=null,
    var BannerPath:String?=null
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Id)
        parcel.writeString(EntityName)
        parcel.writeString(ContactName)
        parcel.writeString(EmailId)
        parcel.writeString(ContactNo)
        parcel.writeString(Address)
        parcel.writeInt(StateId)
        parcel.writeString(StateName)
        parcel.writeString(City)
        parcel.writeString(PINCode)
        parcel.writeString(LandMark)
        parcel.writeString(OpeningTime)
        parcel.writeString(ClosingTime)
        parcel.writeString(Lattitude)
        parcel.writeString(Longitude)
        parcel.writeString(BannerPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EntityModel> {
        override fun createFromParcel(parcel: Parcel): EntityModel {
            return EntityModel(parcel)
        }

        override fun newArray(size: Int): Array<EntityModel?> {
            return arrayOfNulls(size)
        }
    }

}