package com.naayee.naayeeclient.codes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.naayee.naayeeclient.codes.activity.MainActivity.Companion.INTENT_KEY_ENTITY
import com.naayee.naayeeclient.codes.adapters.ShopServicesAdapter
import com.naayee.naayeeclient.codes.interfaces.ServiceItemClickListener
import com.naayee.naayeeclient.codes.model.*
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.storage.LocalStorage
import com.naayee.naayeeclient.codes.utility.Mapper
import com.naayee.naayeeclient.codes.viewmodel.EntityProfileViewModel
import com.naayee.naayeeclient.databinding.ActivityShopProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ShopProfileActivity : AppCompatActivity(), ServiceItemClickListener {

    private val entityProfileViewModel: EntityProfileViewModel? by viewModels()
    private var prServiceListRecyclerView: RecyclerView? = null
    private var shopServicesAdapter: ShopServicesAdapter? = null
    lateinit var binding: ActivityShopProfileBinding
    private var shopAddress: TextView? = null
    private var shopTime: TextView? = null

    private var intentEntityModel: EntityModel? = null

    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var mapper: Mapper
    private var selectedItems: List<ServiceModel>? = null

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Profile"
        getDataFromIntent()
        setUpViews()
        setUpClickListeners()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.also {
            prServiceListRecyclerView = it.prServiceList
            shopAddress = it.prAddress
            shopTime = it.prShopTime
        }
        shopServicesAdapter = ShopServicesAdapter()
        prServiceListRecyclerView?.adapter = shopServicesAdapter

    }

    private fun getDataFromIntent() {
        if (intent.hasExtra(INTENT_KEY_ENTITY)) {
            intentEntityModel = intent.getParcelableExtra(INTENT_KEY_ENTITY)
        }
    }

    private fun setUpClickListeners() {
        binding.proceedBtn.setOnClickListener {
            makeApiCallForAddToCart()
        }
    }

    private fun setUpObservers() {
        entityProfileViewModel?.apply {
            entityProfileLiveData.observe(this@ShopProfileActivity) { response ->
                if (response != null) {
                    parseNetworkResponseForEntityProfile(response)
                }
            }
            shopServicesLiveData.observe(this@ShopProfileActivity) { response ->
                if (response != null) {
                    parseNetworkResponseForEntityServices(response)
                }
            }
            addToCartLiveData.observe(this@ShopProfileActivity) { response ->
                parseAddToCartNetworkResponse(response)
            }
        }
        getShopDetailsAndServices()
    }

    private fun getShopDetailsAndServices() {
        if (intentEntityModel != null) {
            entityProfileViewModel?.getShopProfileAndService(42)
        }
    }

    private fun parseNetworkResponseForEntityProfile(response: GlobalNetResponse<ShopProfileModel>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse: ShopProfileModel? = response.value
                if (successResponse?.Status == 1) {
                    shopAddress?.text = successResponse.entityModel?.Address + ", " + successResponse.entityModel?.StateName + ", " + successResponse.entityModel?.City + " - " + successResponse.entityModel?.PINCode
                    shopTime?.text = successResponse.entityModel?.OpeningTime + " - " + successResponse.entityModel?.ClosingTime
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
        }
    }

    private fun parseNetworkResponseForEntityServices(response: GlobalNetResponse<ShopServicesData>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse: ShopServicesData? = response.value
                if (successResponse?.serviceModel != null) {
                    shopServicesAdapter?.submitData(successResponse.serviceModel!!)
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
        }
    }

    private fun parseAddToCartNetworkResponse(response: GlobalNetResponse<JsonObject>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse = response.value
                val items = gson.fromJson(successResponse,CartResponseModel::class.java)
                moveToBookingSummary(items)
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
        }
    }

    override fun add(serviceModel: ServiceModel) {
        serviceModel.ItemCount += 1
        shopServicesAdapter?.notifyDataSetChanged()
    }

    override fun minus(serviceModel: ServiceModel) {
        serviceModel.ItemCount -= 1
        shopServicesAdapter?.notifyDataSetChanged()
    }

    private fun makeApiCallForAddToCart() {
        shopServicesAdapter?.getSelectedServiceList()?.let {
            if (it.isNotEmpty()) {
                selectedItems = it
                val jsonArray = mapper.convertServicesListToSelectedItemList(selectedItems)
                if (jsonArray != null) {
                    entityProfileViewModel?.addToCartApiCall(entityId = 42.toString(), jsonArray)
                }
            } else {
                Toast.makeText(this, "Please Select", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToBookingSummary(cartResponseModel: CartResponseModel) {
        if (selectedItems != null) {
            localStorage.storeSelectedServices(selectedItems)
        }
        val intent = Intent(this, ServiceBookingActivity::class.java)
        intent.putExtra(CART_RESPONSE, cartResponseModel)
        startActivity(intent)
    }

    companion object {
        const val ENTITY_ID = "entity_id"
        const val ORDER_ID = "order_id"
        const val CART_RESPONSE = "cart_response"
    }

}