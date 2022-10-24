package com.naayee.naayeeclient.codes.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.viewModels

import androidx.databinding.DataBindingUtil
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.RegisterResponseData
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.utility.Utility
import com.naayee.naayeeclient.codes.viewmodel.RegisterViewModel
import com.naayee.naayeeclient.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel? by viewModels()
    private var progressBar: ProgressBar? = null
    private var alreadyRegisterBtn: Button? = null
    private var registerBtn: Button? = null
    private var emailIdedittext: EditText? = null
    private var signupActivityBinding: ActivitySignupBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        val actionBar = supportActionBar
        actionBar!!.title = "NaaYee - Sign Up"

        setUpViews()
        setUpObserves()
        setUpClickListener()

    }

    private fun setUpViews() {
        signupActivityBinding?.let { activitySignupBinding ->
            emailIdedittext = activitySignupBinding.emailid
            registerBtn = activitySignupBinding.register
            alreadyRegisterBtn = activitySignupBinding.alreadyregister
        }
    }

    private fun setUpObserves() {
        registerViewModel?.apply {
            this.authLiveData.observe(this@SignupActivity) { globalNetworkResponse ->
                parseRegisterDataResponse(globalNetworkResponse)
            }
        }
    }

    private fun parseRegisterDataResponse(response: GlobalNetResponse<RegisterResponseData>) {
        /*progressBar?.showHideView(false)*/
        when (response) {
            is GlobalNetResponse.NetworkFailure -> {
                val errorMsg = response.error
                Utility.showToastMessage(errorMsg)
            }
            is GlobalNetResponse.Success -> {
                val registerResponseData: RegisterResponseData? = response.value
                if (registerResponseData?.status == "1") {
                   /* AuthConfigManager.saveAuthToken(registerResponseData.authtoken)*/
                    signupActivityBinding?.root?.let { Utility.showToastMessage(registerResponseData.message!!) }
                    startVerificationActivity()
                } else {
                    if (registerResponseData?.message != null) {
                        signupActivityBinding?.root?.let { Utility.showToastMessage(registerResponseData.message!!) }
                    }
                }
            }
        }
    }


    private fun setUpClickListener() {
        registerBtn?.setOnClickListener {
            val inputEmail: String = emailIdedittext?.text.toString()
            if (inputEmail.isNotEmpty()) {
//                progressBar?.showHideView(true)
                registerViewModel?.submitVerifyOtpData (inputEmailId = inputEmail)

            } else {
                Utility.showToastMessage("Enter Valid Email-Id")
            }
        }
        /*if (Utility.isUserLoggedIn()) {
            loginActivityBinding?.notAMember?.showHideView(false)
        } else {
            loginActivityBinding?.notAMember?.showHideView(true)
        }*/
        signupActivityBinding?.alreadyregister?.setOnClickListener {
            startLoginActivity()
        }

    }

    private fun startLoginActivity() {
        val loginupActivityIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginupActivityIntent)
        finish()
    }

    private fun startVerificationActivity() {
        val verifyActivityIntent = Intent(this, VerificationActivity::class.java)
        startActivity(verifyActivityIntent)
        finish()
    }
}