package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseData(
    @SerializedName("Message")
    var message: String? = null,

    @SerializedName("Status")
    var status: String? = null,

    @SerializedName("MobileNo")
    var mobiileno: String? = null,
)
