package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.naayee.naayeeclient.codes.model.SelectedItem
import com.naayee.naayeeclient.codes.model.ShopProfileModel
import com.naayee.naayeeclient.codes.model.ShopServicesData
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EntityProfileViewModel @Inject constructor(var networkCallRepository: NetworkCallRepository) : ViewModel() {
    var entityProfileLiveData: MutableLiveData<GlobalNetResponse<ShopProfileModel>> = MutableLiveData()
    var shopServicesLiveData: MutableLiveData<GlobalNetResponse<ShopServicesData>> = MutableLiveData()
    var addToCartLiveData: MutableLiveData<GlobalNetResponse<JsonObject>> = MutableLiveData()

    fun getShopProfileAndService(entityId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            /* val authToken = AuthConfigManager.getAuthToken()*/
            val profileResponse = async { networkCallRepository.postEntityProfile(entityId) }
            val serviceResponse = async { networkCallRepository.postEntityServices(entityId) }
            entityProfileLiveData.postValue(profileResponse.await())
            shopServicesLiveData.postValue(serviceResponse.await())
        }
    }

    fun addToCartApiCall(entityId: String, servicesList: ArrayList<SelectedItem>?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = networkCallRepository.addToCartApiServer(entityId, servicesList)
            addToCartLiveData.postValue(response)
        }
    }
}