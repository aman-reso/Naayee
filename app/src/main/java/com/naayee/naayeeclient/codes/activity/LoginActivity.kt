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
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager
import com.naayee.naayeeclient.databinding.ActivityLoginBinding
import com.naayee.naayeeclient.codes.extensions.showHideView
import com.naayee.naayeeclient.codes.model.LoginResponseData
import com.naayee.naayeeclient.codes.network_res.GlobalNetResponse
import com.naayee.naayeeclient.codes.utility.Utility
import com.naayee.naayeeclient.codes.utility.Utility.showToastMessage
import com.naayee.naayeeclient.codes.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel? by viewModels()
    private var progressBar: ProgressBar? = null
    private var notAMemberBtn: Button? = null
    private var loginBtn: Button? = null
    private var emailIdedittext: EditText? = null
    private var loginActivityBinding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.title = "NaaYee - Sign In"

        setUpViews()
        setUpObserves()
        setUpClickListener()

    }

    private fun setUpObserves() {
        loginViewModel?.apply {
            this.authLiveData.observe(this@LoginActivity) { globalNetworkResponse ->
                parseLoginDataResponse(globalNetworkResponse)
            }
        }
    }

    private fun parseLoginDataResponse(response: GlobalNetResponse<LoginResponseData>) {
        /*progressBar?.showHideView(false)*/
        when (response) {
            is GlobalNetResponse.NetworkFailure -> {
                val errorMsg = response.error
                showToastMessage(errorMsg)
            }
            is GlobalNetResponse.Success -> {
                val loginResponseData: LoginResponseData? = response.value
                if (loginResponseData?.status == 1) {
                    AuthConfigManager.saveAuthToken(loginResponseData.authToken)
                    loginActivityBinding?.root?.let { showToastMessage(loginResponseData.message!!) }
                    startHomeLandingActivity()
                } else {
                    if (loginResponseData?.message != null) {
                        loginActivityBinding?.root?.let { showToastMessage(loginResponseData.message!!) }
                    }
                }
            }
        }
    }

    private fun startHomeLandingActivity() {
        val verification = Intent(this, VerificationActivity::class.java)
        val email = emailIdedittext?.text.toString()
        verification.putExtra("emailid",email)
        startActivity(verification)
        finish()
    }


    private fun setUpViews() {
        loginActivityBinding?.let { activityLoginBinding ->
            emailIdedittext = activityLoginBinding.emailid
            loginBtn = activityLoginBinding.login
            notAMemberBtn = activityLoginBinding.notAMember
        }
    }

    private fun setUpClickListener() {
        loginBtn?.setOnClickListener {
            val inputEmail: String = emailIdedittext?.text.toString()
            if (inputEmail.isNotEmpty()) {
//                progressBar?.showHideView(true)
                loginViewModel?.submitLoginData(inputEmailId = inputEmail)

            } else {
                showToastMessage("Enter Valid Registered Email-id")
            }
        }
        if (Utility.isUserLoggedIn()) {
            loginActivityBinding?.notAMember?.showHideView(false)
        } else {
            loginActivityBinding?.notAMember?.showHideView(true)
        }
        loginActivityBinding?.notAMember?.setOnClickListener {
            startRegistrationActivity()
        }

    }

    private fun startRegistrationActivity() {
        val signupActivityActivityIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupActivityActivityIntent)
        finish()
    }

}