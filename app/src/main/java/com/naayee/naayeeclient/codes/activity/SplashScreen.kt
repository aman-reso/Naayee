package com.naayee.naayeeclient.codes.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.extensions.showHideView
import com.naayee.naayeeclient.codes.utility.LanguageManager
import com.naayee.naayeeclient.codes.utility.Utility
import com.naayee.naayeeclient.codes.utility.Utility.checkIsOnline
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseActivity() {
    var pgBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        pgBar = findViewById(R.id.pgBar)
        pgBar?.showHideView(true)
        if (!checkIsOnline()) {
            Utility.showToastMessage(LanguageManager.getStringInfo(R.string.no_internet_connection))
        }
    }

    private fun navigateForward() {
        lifecycleScope.launch {
            if (Utility.isUserLoggedIn()) {
                startHomeLandingActivity()
            } else {
                startLoginActivity()
            }
        }
    }

    override fun currentLocation(location: Location?) {
        super.currentLocation(location)
        navigateForward()
    }

    override fun locationCancelled() {
        super.locationCancelled()
        Utility.showToastMessage("Please allow permission")
        finish()
    }

    private fun startLoginActivity() {
        val homeLandingIntent = Intent(this, LoginActivity::class.java)
        startActivity(homeLandingIntent)
        finish()
    }


    private fun startHomeLandingActivity() {
        val homeLandingIntent = Intent(this@SplashScreen, LoginActivity::class.java)
        startActivity(homeLandingIntent)
        finish()
    }
}