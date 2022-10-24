package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class LoginRequestData(
    @SerializedName("MobileNo")
    var email: String,
)
