package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naayee.naayeeclient.codes.model.RecommendationsModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCatShopListViewModel @Inject constructor(var networkCallRepository: NetworkCallRepository): ViewModel() {
    var subCatShopListLiveData: MutableLiveData<GlobalNetResponse<RecommendationsModel>> = MutableLiveData()

    fun getSubCatShopList(catid:Int,lat: String?, lng: String?) {
        if (lat != null && lng != null) {
            viewModelScope.launch(Dispatchers.IO) {
                /*val authToken = AuthConfigManager.getAuthToken()*/
                val response = networkCallRepository.postSubCatShopList(catid,lat, lng)
                subCatShopListLiveData.postValue(response)
            }
        }
    }
}