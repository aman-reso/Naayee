package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager
import com.naayee.naayeeclient.codes.model.RecommendationsModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecomendListViewModel @Inject constructor(var networkCallRepository: NetworkCallRepository) : ViewModel(){
    var recomendListLiveData: MutableLiveData<GlobalNetResponse<RecommendationsModel>> = MutableLiveData()

    fun getContactsListForMessage(lat: String?, lng: String?) {
        if (lat != null && lng != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val authToken = AuthConfigManager.getAuthToken()
                val response = networkCallRepository.postRecomendList(lat, lng)
                recomendListLiveData.postValue(response)
            }
        }
    }
}