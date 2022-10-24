package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naayee.naayeeclient.codes.model.RegisterResponseData
import com.naayee.naayeeclient.codes.model.VerifyOtpResponseData
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var networkCallRepository: NetworkCallRepository) : ViewModel() {
    internal var authLiveData: MutableLiveData<GlobalNetResponse<RegisterResponseData>> = MutableLiveData()

    internal fun submitVerifyOtpData(inputEmailId:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = networkCallRepository.submitRequestForRegister(inputEmailId)
            authLiveData.postValue(response)
        }
    }
}