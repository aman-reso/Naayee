package com.naayee.naayeeclient.codes.model

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponseData(
    @SerializedName("Message")
    var message: String? = null,

    @SerializedName("Status")
    var status: String? = null,

    @SerializedName("AuthToken")
    var authtoken: String? = null,

    @SerializedName("DisplayName")
    var displayname: String? = null,
)
