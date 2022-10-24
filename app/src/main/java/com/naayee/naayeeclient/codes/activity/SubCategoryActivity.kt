package com.naayee.naayeeclient.codes.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.codes.adapters.SubCategoryListAdapter
import com.naayee.naayeeclient.codes.model.SubCategoryModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.viewmodel.SubCategoryViewModel
import com.naayee.naayeeclient.databinding.ActivitySubCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryActivity : AppCompatActivity() {
    private val subCatListViewModel: SubCategoryViewModel? by viewModels()
    private var subCatActivityBinding: ActivitySubCategoryBinding? = null
    private var subCategoryRecyclerView : RecyclerView? = null
    private var subCatlistadapter : SubCategoryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subCatActivityBinding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(subCatActivityBinding?.root)
        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        subCatListViewModel?.apply {
            subCatListliveData.observe(this@SubCategoryActivity) { response ->
                if (response != null) {
                    parseNetworkResponseForSubCatList(response)
                }
            }
            getSubCatList(1)
        }
    }

    private fun parseNetworkResponseForSubCatList(response: GlobalNetResponse<SubCategoryModel>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse: SubCategoryModel? = response.value
                if (successResponse?.serviceList != null) {
                    subCatlistadapter?.submitData(successResponse.serviceList!!)
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
            else -> {
                //nothing to do just show a toast message
            }
        }
    }

    private fun setUpViews() {
        subCatActivityBinding.also {
            subCategoryRecyclerView = it?.subCategoryRecyclerView
        }
        subCatlistadapter= SubCategoryListAdapter()
        subCategoryRecyclerView?.adapter = subCatlistadapter
    }
}