package com.naayee.naayeeclient.codes.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.codes.adapters.SubCatShopListAdapter
import com.naayee.naayeeclient.codes.model.RecommendationsModel
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.viewmodel.SubCatShopListViewModel
import com.naayee.naayeeclient.databinding.ActivitySubCategoryShopListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryShopList : AppCompatActivity() {

    private val subCatShopListViewModel: SubCatShopListViewModel? by viewModels()
    //private var subCatShopListActivityBinding: ActivitySubCategoryShopListBinding? = null
    private var subCatshopListRecyclerView: RecyclerView? = null
    private var subcatshopliatadapter : SubCatShopListAdapter? = null
    lateinit var binding : ActivitySubCategoryShopListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sub_category_shop_list)
        binding = ActivitySubCategoryShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.also {
            subCatshopListRecyclerView = it.subCatshopListRecyclerView
        }
        subcatshopliatadapter= SubCatShopListAdapter()
        subCatshopListRecyclerView?.adapter = subcatshopliatadapter
    }

    private fun setUpObservers() {
        subCatShopListViewModel?.apply {
            subCatShopListLiveData.observe(this@SubCategoryShopList) { response ->
                if (response != null) {
//                    progressBar?.showHideView(false)
                    parseNetworkResponseForSubCatShopList(response)
                }
            }
            getSubCatShopList(1,"25.3","56.2")
        }
//        progressBar?.showHideView(true)
//        messageTemplateViewModel?.getWAMessageTemplate()
    }

    private fun parseNetworkResponseForSubCatShopList(response: GlobalNetResponse<RecommendationsModel>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse: RecommendationsModel? = response.value
//                println("successResponse-->$successResponse")
                if (successResponse?.entityList != null) {
                    subcatshopliatadapter?.submitData(successResponse.entityList!!)
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
            else -> {
                //nothing to do just show a toast message
            }
        }
    }

}