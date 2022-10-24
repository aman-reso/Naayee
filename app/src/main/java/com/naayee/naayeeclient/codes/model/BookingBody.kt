package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class BookingBody(
    @SerializedName("Name")
    var name: String?="",
    @SerializedName("MobileNo")
    var mobileNum: String?="",
    @SerializedName("BookingDate")
    var bookingDate: String?="",
    @SerializedName("FromTime")
    var timeSlot: String?="",
    @SerializedName("PaymentMode")
    var paymentMode: Int?=-1,
    @SerializedName("TotalAmt")
    var totalAmount: String?="",
    @SerializedName("TotalDiscount")
    var totalDiscount: String?="",
    @SerializedName("TotalGST")
    var totalGst: String?=""
)
