package com.naayee.naayeeclient.codes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager
import com.naayee.naayeeclient.codes.model.SubCategoryModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.repository.NetworkCallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoryViewModel @Inject constructor(var networkCallRepository: NetworkCallRepository) : ViewModel() {
    var subCatListliveData: MutableLiveData<GlobalNetResponse<SubCategoryModel>> = MutableLiveData()

    fun getSubCatList(catid:Int){
        if (catid != 0) {
            viewModelScope.launch(Dispatchers.IO) {
              /*  val authToken = AuthConfigManager.getAuthToken()*/
                val response = networkCallRepository.getSubCategoryList(catid)
                subCatListliveData.postValue(response)
            }
        }
    }
}