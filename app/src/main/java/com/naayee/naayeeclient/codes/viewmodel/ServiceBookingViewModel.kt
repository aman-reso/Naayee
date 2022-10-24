package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.naayee.naayeeclient.codes.model.BookingBody
import com.naayee.naayeeclient.codes.model.PaymentCnfReqModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceBookingViewModel @Inject constructor(var repository: NetworkCallRepository) : ViewModel() {
    var offlineBookingLiveData = MutableLiveData<GlobalNetResponse<JsonObject>>()
    var onlineBookingLiveData = MutableLiveData<GlobalNetResponse<JsonObject>>()
    var paymentCnfResLiveData=MutableLiveData<GlobalNetResponse<JsonObject>>()

    fun makeBookingOffline(orderId: String, bookingBody: BookingBody?) = viewModelScope.launch {
        if (bookingBody != null) {
            val response = repository.makeBookingOffline(orderId = orderId, bookingBody = bookingBody)
            offlineBookingLiveData.postValue(response)
        }
    }

    fun makeBookingOnline(orderId: String, bookingBody: BookingBody) = viewModelScope.launch {
        val response = repository.makeBookingOnline(orderId = orderId,bookingBody)
        onlineBookingLiveData.postValue(response)

    }

    fun onlineModePaymentConfirmation(paymentCnfReqModel: PaymentCnfReqModel?) = viewModelScope.launch {
        if (paymentCnfReqModel != null) {
            val response = repository.paymentValidation(paymentCnfReqModel)
            paymentCnfResLiveData.postValue(response)
        }
    }
}