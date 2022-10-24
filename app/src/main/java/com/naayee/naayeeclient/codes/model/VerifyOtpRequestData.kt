package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequestData(
    @SerializedName("OTP")
    var otp: String? = null,

    @SerializedName("MobileNo")
    var mobileno: String? = null,

    @SerializedName("DeviceId")
    var deviceid: String? = null,

    @SerializedName("AndVersion")
    var andversion: String? = null,

    @SerializedName("IpAddress")
    var ipaddress: String? = null,

    @SerializedName("Lattitude")
    var lattitude: String? = null,

    @SerializedName("Longitude")
    var longitude: String? = null,

)
