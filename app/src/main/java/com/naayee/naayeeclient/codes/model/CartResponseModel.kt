package com.naayee.naayeeclient.codes.model

import android.os.Parcel
import android.os.Parcelable

data class CartResponseModel(
    val EntityId: Int?=0,
    val Message: String?="",
    val OrderId: Int?=0,
    val Status: Int?=0,
    val TotalAmt: Double?=0.0,
    val TotalDiscount: Double?=0.0,
    val TotalDiscountP: Double?=0.0,
    val TotalGST: Double?=0.0,
    val TotalGSTP: Double?=0.0
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(EntityId)
        parcel.writeString(Message)
        parcel.writeValue(OrderId)
        parcel.writeValue(Status)
        parcel.writeValue(TotalAmt)
        parcel.writeValue(TotalDiscount)
        parcel.writeValue(TotalDiscountP)
        parcel.writeValue(TotalGST)
        parcel.writeValue(TotalGSTP)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartResponseModel> {
        override fun createFromParcel(parcel: Parcel): CartResponseModel {
            return CartResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<CartResponseModel?> {
            return arrayOfNulls(size)
        }
    }

}