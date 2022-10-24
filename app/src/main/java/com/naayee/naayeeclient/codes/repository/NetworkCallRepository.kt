package com.naayee.naayeeclient.codes.repository

import com.naayee.naayeeclient.codes.activity.LocationValue
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager
import com.naayee.naayeeclient.codes.model.*
import com.naayee.naayeeclient.codes.path.NaayeeApiInterface
import com.naayee.naayeeclient.codes.path.safeApiCall
import com.naayee.naayeeclient.codes.utility.AndroidDeviceUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkCallRepository @Inject constructor(var apiInterface: NaayeeApiInterface) {

    suspend fun submitRequestForLogin(inputEmailId: String) = safeApiCall {

        val email: String = inputEmailId
        val loginRequestData = LoginRequestData(
            email
        )
        apiInterface.postLoginData(loginRequestData = loginRequestData)
    }

    suspend fun submitRequestForVeriftyOtp(inputotp: String, inputemailid: String) = safeApiCall {

        val otp: String = inputotp
        val mobileno: String = inputemailid
        val deviceid: String = AndroidDeviceUtils.getDeviceId()
        val andversion: String = AndroidDeviceUtils.getAndroidVersion()
        val ipaddress: String = AndroidDeviceUtils.getLocalIpAddress()
        val lattitude: String = LocationValue.latitude
        val longitude: String = LocationValue.longitude
        val verifyotpRequestData = VerifyOtpRequestData(
            otp, mobileno, deviceid, andversion, ipaddress, lattitude, longitude
        )

        apiInterface.postVerifyOtpData(verifyOtpRequestData = verifyotpRequestData)
    }

    suspend fun submitRequestForRegister(inputemailid: String) = safeApiCall {

        val mobileno: String = inputemailid
        val deviceid: String = AndroidDeviceUtils.getDeviceId()
        val andversion: String = AndroidDeviceUtils.getAndroidVersion()
        val ipaddress: String = AndroidDeviceUtils.getLocalIpAddress()
        val lattitude: String = LocationValue.latitude
        val longitude: String = LocationValue.longitude
        val registerRequestData = RegisterRequestData(
            mobileno, deviceid, andversion, ipaddress, lattitude, longitude
        )

        apiInterface.postRegisterData(registerRequestData = registerRequestData)
    }

    suspend fun postRecomendList(lat: String, lng: String) = safeApiCall {
        apiInterface.postRecomendList(lat, lng)
    }

    suspend fun getSubCategoryList(catid: Int) = safeApiCall {
        apiInterface.getSubCategoryList(catid)
    }

    suspend fun postSubCatShopList(catid: Int, lat: String, lng: String) = safeApiCall {
        apiInterface.postSubCatShopList(catid, lat, lng)
    }

    suspend fun postEntityProfile(entityid: Int) = safeApiCall {
        apiInterface.postEntityProfile(entityid)
    }

    suspend fun postEntityServices(entityid: Int) = safeApiCall {
        apiInterface.postEntityServices(entityid)
    }

    suspend fun addToCartApiServer(entityId: String, servicesList: ArrayList<SelectedItem>?) = safeApiCall {
        val token = AuthConfigManager.getAuthToken()
        apiInterface.addToCart(token, entityId, serviceLists = servicesList)
    }

    suspend fun makeBookingOffline(orderId:String,bookingBody: BookingBody)= safeApiCall {
        val token = AuthConfigManager.getAuthToken()
        apiInterface.makeBookingOffline(token, orderId = orderId,bookingBody)
    }

    suspend fun makeBookingOnline(orderId: String, bookingBody: BookingBody)= safeApiCall {
        val token = AuthConfigManager.getAuthToken()
        apiInterface.makeOnlineBooking(token, orderId = orderId,bookingBody)
    }
    suspend fun paymentValidation(paymentCnfReqModel: PaymentCnfReqModel) = safeApiCall {
        val token = AuthConfigManager.getAuthToken()
        apiInterface.onlinePaymentResponse(token,paymentCnfReqModel)
    }

}