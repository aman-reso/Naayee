package com.naayee.naayeeclient.codes.path

import com.google.gson.JsonObject
import com.naayee.naayeeclient.codes.model.*
import retrofit2.http.*

interface NaayeeApiInterface {

    @Headers(value = ["Content-Type: application/json", "Accept: application/json"])
    @POST(value = "/Api/Account/LoginByOTP")
    suspend fun postLoginData(
        @Body loginRequestData: LoginRequestData
    ): LoginResponseData

    @Headers(value = ["Content-Type: application/json", "Accept: application/json"])
    @POST(value = "/Api/Account/VerifyOTP")
    suspend fun postVerifyOtpData(
        @Body verifyOtpRequestData: VerifyOtpRequestData
    ): VerifyOtpResponseData

    @Headers(value = ["Content-Type: application/json", "Accept: application/json"])
    @POST(value = "/Api/Account/Register")
    suspend fun postRegisterData(
        @Body registerRequestData: RegisterRequestData
    ): RegisterResponseData

    @POST("/Api/Entity/Recomendations")
    suspend fun postRecomendList(@Query("lat") lattitude: String, @Query("lng") longitude: String): RecommendationsModel

    @GET("/Api/Services/SubCategory")
    suspend fun getSubCategoryList(@Query("CatId") catid: Int): SubCategoryModel

    @POST("/Api/Entity/ListByCategoryId")
    suspend fun postSubCatShopList(@Query("categoryId") catid: Int, @Query("lat") lattitude: String, @Query("lng") longitude: String): RecommendationsModel

    @POST("/Api/Entity/Profile")
    suspend fun postEntityProfile(@Query("EntityId") entityid: Int): ShopProfileModel

    @POST("/Api/Entity/Services")
    suspend fun postEntityServices(@Query("EntityId") entityid: Int): ShopServicesData

    @POST("/Api/Order/Cart")
    suspend fun addToCart(@Query("AuthToken") authToken: String, @Query("EntityId") entityId: String, @Body serviceLists: ArrayList<SelectedItem>?): JsonObject

    @POST("/Api/Order/Booking")
    suspend fun makeBookingOffline(@Query("AuthToken") authToken: String, @Query("orderId") orderId: String, @Body booking: BookingBody): JsonObject

    @POST("/Api/Order/OnlinePay")
    suspend fun makeOnlineBooking(@Query("AuthToken") authToken: String, @Query("orderId") orderId: String, @Body booking: BookingBody): JsonObject

    @POST("/Api/Order/OnlinePayResp")
    suspend fun onlinePaymentResponse(@Query("AuthToken") authToken: String, @Body PayTxnResp:PaymentCnfReqModel ): JsonObject

}