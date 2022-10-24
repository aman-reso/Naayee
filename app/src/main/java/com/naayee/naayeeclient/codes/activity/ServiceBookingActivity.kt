package com.naayee.naayeeclient.codes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.NaayeeApplication
import com.naayee.naayeeclient.codes.activity.ShopProfileActivity.Companion.CART_RESPONSE
import com.naayee.naayeeclient.codes.adapters.DateTimeSelectionAdapter
import com.naayee.naayeeclient.codes.adapters.SelectedServiceAdapter
import com.naayee.naayeeclient.codes.model.BookingBody
import com.naayee.naayeeclient.codes.model.CartResponseModel
import com.naayee.naayeeclient.codes.model.PaymentCnfReqModel
import com.naayee.naayeeclient.codes.model.PaymentInitResponse
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.storage.LocalStorage
import com.naayee.naayeeclient.codes.utility.DateTimeSelectionUtils
import com.naayee.naayeeclient.codes.viewmodel.ServiceBookingViewModel
import com.naayee.naayeeclient.databinding.ActivityServiceBookingBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class ServiceBookingActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private val serviceBookingViewModel: ServiceBookingViewModel? by viewModels()

    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var dateTimeSelectionUtil: DateTimeSelectionUtils

    private var binding: ActivityServiceBookingBinding? = null
    private val selectedServiceAdapter: SelectedServiceAdapter? by lazy { SelectedServiceAdapter() }
    private var checkout: Checkout? = null
    private var paymentMode: Int = -1
    private val dateTimeSelectionAdapter: DateTimeSelectionAdapter? by lazy { DateTimeSelectionAdapter() }

    private var cartResponseModel: CartResponseModel? = null

    @Inject
    lateinit var gson: Gson
    private var paymentInitResponse: PaymentInitResponse? = null
    private var bookingBody: BookingBody? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBookingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        handleIntent()
        setUpView()
        getStoredSelectedService()
        setUpClickListeners()
        setUpObservers()
    }

    private fun handleIntent() {
        if (intent.hasExtra(CART_RESPONSE)) {
            cartResponseModel = intent.getParcelableExtra(CART_RESPONSE)
        }
        if (cartResponseModel == null) {
            finish()
        }
    }

    private fun setUpView() {
        binding?.prServiceList?.adapter = selectedServiceAdapter
        val checkedRadioBtnId = binding?.paymentModeRadioGroup?.checkedRadioButtonId
        if (checkedRadioBtnId != null) {
            handlePaymentMode(checkedRadioBtnId)
        }
        setUpDateTimeRecyclerView()
    }

    private fun setUpDateTimeRecyclerView() {
        binding?.dateTimeRecyclerView?.adapter = dateTimeSelectionAdapter
        binding?.dateTimeRecyclerView?.layoutManager = getCustomGridLayoutManager()
        dateTimeSelectionAdapter?.submitList(dateTimeSelectionUtil.prepareData())
    }

    private fun setUpClickListeners() {
        binding?.bookNow?.setOnClickListener {
            if (validateUserInput()) {
                if (paymentMode == OFFLINE_MODE) {
                    makeOfflineBooking()
                } else {
                    makeOnlineBooking()
                }
            }
        }

        binding?.paymentModeRadioGroup?.setOnCheckedChangeListener { _, checkedId ->
            handlePaymentMode(checkedId)
        }
    }

    private fun setUpObservers() {
        serviceBookingViewModel?.apply {
            offlineBookingLiveData.observe(this@ServiceBookingActivity) {
                parseBookingOfflineResponse(it)
            }
            onlineBookingLiveData.observe(this@ServiceBookingActivity) {
                parseOnlineBookingResponse(it)
            }
            paymentCnfResLiveData.observe(this@ServiceBookingActivity) {
                parserPaymentValidationApiResponse(it)
            }
        }
    }

    private fun parserPaymentValidationApiResponse(it: GlobalNetResponse<JsonObject>?) {

    }

    private fun parseBookingOfflineResponse(it: GlobalNetResponse<JsonObject>?) {
        when (it) {
            is GlobalNetResponse.Success -> {
                val successResponse = it.value
                if (successResponse.has("Message")) {
                    val message = successResponse.get("Message").asString
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
                if (successResponse.has("Status")) {
                    val status = successResponse.get("Status").asString
                    if (status.equals("1")) {
                        startBookingDetailActivity()
                    }
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
        }
    }

    private fun parseOnlineBookingResponse(response: GlobalNetResponse<JsonObject>) {
        when (response) {
            is GlobalNetResponse.Success -> {
                val successResponse = response.value
                val paymentInitResponse = gson.fromJson(successResponse, PaymentInitResponse::class.java)
                this.paymentInitResponse = paymentInitResponse
                if (!paymentInitResponse.Rz_order_id.isNullOrEmpty() && !paymentInitResponse.TxnNo.isNullOrEmpty()) {
                    openRazorpayPaymentWindow()
                }
            }
            is GlobalNetResponse.NetworkFailure -> {

            }
        }
    }


    private fun handlePaymentMode(checkedId: Int) {
        if (checkedId == R.id.offlinePaymentModeRdBtn) {
            paymentMode = OFFLINE_MODE
        } else if (checkedId == R.id.onlinePaymentModeRdBtn) {
            paymentMode = ONLINE_MODE
        }
    }

    private fun validateUserInput(): Boolean {
        val name: String? = binding?.personName?.text?.toString()
        val mobileNum = binding?.personMobileNo?.text?.toString()
        if (name.isNullOrEmpty()) {
            showErrorToast("Please enter name")
            return false
        }
        if (mobileNum.isNullOrEmpty()) {
            showErrorToast("Please enter Mobile number")
            return false
        }

        if (paymentMode == -1) {
            showErrorToast("Please select Payment mode")
            return false
        }
        if (dateTimeSelectionAdapter?.getAvailableDateAndSlots()?.first == null || dateTimeSelectionAdapter?.getAvailableDateAndSlots()?.second == null) {
            showErrorToast("Please Select Date or Time slot")
            return false
        }
        if (bookingBody == null) {
            bookingBody = BookingBody()
        }
        bookingBody?.bookingDate = dateTimeSelectionAdapter?.getAvailableDateAndSlots()?.first
        bookingBody?.name = name
        bookingBody?.mobileNum = mobileNum
        bookingBody?.timeSlot = dateTimeSelectionAdapter?.getAvailableDateAndSlots()?.second
        return true
    }

    private fun showErrorToast(msg: String?) {
        if (msg == null) {
            return
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun openRazorpayPaymentWindow() {
        if (checkout == null) {
            checkout = Checkout()
        }
        if (paymentInitResponse != null) {
            try {
                Checkout.preload(NaayeeApplication.getNaayeeAppContext())
                checkout?.setFullScreenDisable(true)
                val options = JSONObject()
                options.put("name", "Naayee")
                options.put("image", R.drawable.naayee1)
                options.put("order_id", paymentInitResponse!!.Rz_order_id)
                val prefill = JSONObject()
                if (paymentInitResponse != null) {
                    if (!paymentInitResponse?.EmailId.isNullOrEmpty()) {
                        prefill.put("email", paymentInitResponse?.EmailId)
                    }
                    if (!paymentInitResponse?.MobileNo.isNullOrEmpty()) {
                        prefill.put("contact", paymentInitResponse?.MobileNo)
                    }
                } else {
                    prefill.put("email", "test@gmail.com")
                    prefill.put("contact", "1234567890")
                }
                options.put("prefill", prefill)
                checkout?.open(this, options)
            } catch (e: Exception) {
                Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    private fun getStoredSelectedService() {
        selectedServiceAdapter?.submitList(localStorage.getSelectedServices())
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
        goAheadForPaymentValidation(p0, p1)
    }

    private fun goAheadForPaymentValidation(p0: String?, p1: PaymentData?) {
        if (p1 == null) {
            return
        }
        if (paymentInitResponse == null) {
            return
        }
        val paymentCnfReqModel = PaymentCnfReqModel()
        paymentCnfReqModel.Rz_payment_id = p1.paymentId
        paymentCnfReqModel.Rz_signature = p1.signature
        paymentCnfReqModel.Rz_order_id = p1.orderId
        paymentCnfReqModel.OrderId = paymentInitResponse!!.OrderId
        paymentCnfReqModel.TxnNo = paymentInitResponse!!.TxnNo
        paymentCnfReqModel.ErrorMsg = p0
        serviceBookingViewModel?.onlineModePaymentConfirmation(paymentCnfReqModel)
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()
    }

    private fun startBookingDetailActivity() {
        val intent = Intent(this, BookingDetailsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getCustomGridLayoutManager(): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(this, 5)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (dateTimeSelectionAdapter?.getItemViewType(position)) {
                    TYPE_TITLE -> 5
                    TYPE_DATE -> 1
                    TYPE_TIME -> 1
                    else -> 1
                }
            }
        }
        return gridLayoutManager
    }

    private fun makeOfflineBooking() {
        if (cartResponseModel != null && cartResponseModel?.OrderId != null) {
            bookingBody?.paymentMode = OFFLINE_MODE
            bookingBody?.totalAmount = cartResponseModel!!.TotalAmt.toString()
            bookingBody?.totalGst = cartResponseModel!!.TotalGST.toString()
            bookingBody?.totalDiscount = cartResponseModel!!.TotalDiscount.toString()
            serviceBookingViewModel?.makeBookingOffline(cartResponseModel?.OrderId.toString(), bookingBody)
        }
    }

    private fun makeOnlineBooking() {
        if (cartResponseModel != null && cartResponseModel?.OrderId != null) {
            val bookingBody = BookingBody()
            bookingBody.paymentMode = ONLINE_MODE
            bookingBody.totalAmount = cartResponseModel!!.TotalAmt.toString()
            bookingBody.totalGst = cartResponseModel!!.TotalGST.toString()
            bookingBody.totalDiscount = cartResponseModel!!.TotalDiscount.toString()
            serviceBookingViewModel?.makeBookingOnline(cartResponseModel!!.OrderId.toString(), bookingBody)
        }
    }


    companion object {
        const val OFFLINE_MODE = 1
        const val ONLINE_MODE = 2
        const val TYPE_TITLE = 1
        const val TYPE_TIME = 2
        const val TYPE_DATE = 3
    }

}