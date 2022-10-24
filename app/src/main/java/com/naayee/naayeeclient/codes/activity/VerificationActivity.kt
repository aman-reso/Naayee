package com.naayee.naayeeclient.codes.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager

import com.naayee.naayeeclient.codes.model.VerifyOtpResponseData
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.utility.Utility
import com.naayee.naayeeclient.codes.viewmodel.VerifyViewModel
import com.naayee.naayeeclient.databinding.ActivityVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {
    private val verifyOtpViewModel: VerifyViewModel? by viewModels()
    private var progressBar: ProgressBar? = null
    private var verifyBtn: Button? = null
    private var etotpEditText: EditText? = null
    private var emaillbl: TextView? = null
    private var verifyActivityBinding: ActivityVerificationBinding? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_verification)

        val actionBar = supportActionBar
        actionBar!!.title = "NaaYee - Verification"

        email = intent.getStringExtra("emailid")

        setUpViews()
        setUpObserves()
        setUpClickListener()

    }

    private fun setUpObserves() {
        verifyOtpViewModel?.apply {
            this.authLiveData.observe(this@VerificationActivity) { globalNetworkResponse ->
                parseVerifyOtpDataResponse(globalNetworkResponse)
            }
        }
    }

    private fun parseVerifyOtpDataResponse(response: GlobalNetResponse<VerifyOtpResponseData>) {
        /*progressBar?.showHideView(false)*/
        when (response) {
            is GlobalNetResponse.NetworkFailure -> {
                val errorMsg = response.error
                Utility.showToastMessage(errorMsg)
            }
            is GlobalNetResponse.Success -> {
                val verifyOtpResponseData: VerifyOtpResponseData? = response.value
                if (verifyOtpResponseData?.authtoken != null) {
                    AuthConfigManager.saveAuthToken(verifyOtpResponseData.authtoken)
                    verifyActivityBinding?.root?.let { Utility.showToastMessage(verifyOtpResponseData.message!!) }
                    startHomeLandingActivity()
                } else {
                    if (verifyOtpResponseData?.message != null) {
                        verifyActivityBinding?.root?.let { Utility.showToastMessage(verifyOtpResponseData.message!!) }
                    }
                }
            }
        }
    }


    private fun setUpViews() {
        verifyActivityBinding?.let { activityVerifyBinding ->
            emaillbl = activityVerifyBinding.emaillbl
            verifyBtn = activityVerifyBinding.verifyotp
            etotpEditText = activityVerifyBinding.etotp
        }
        verifyActivityBinding?.emaillbl?.text = email
    }


    private fun setUpClickListener() {
        verifyBtn?.setOnClickListener {
            val inputEmail: String = emaillbl?.text.toString()
            val inputOtp: String = etotpEditText?.text.toString()
            if (inputEmail.isNotEmpty() && inputOtp.isNotEmpty()) {
                verifyOtpViewModel?.submitVerifyOtpData (inputEmailId = inputEmail, inputotp = inputOtp)
            } else {
                Utility.showToastMessage("Enter Valid OTP, sent in your Email")
            }
        }
        verifyActivityBinding?.verifyotp?.setOnClickListener {
            startHomeLandingActivity()
        }

    }

    private fun startHomeLandingActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

}