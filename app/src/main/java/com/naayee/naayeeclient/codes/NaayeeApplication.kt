package com.naayee.naayeeclient.codes

import android.app.Application
import android.content.Context
import android.os.Build
import com.naayee.naayeeclient.codes.utility.LanguageManager
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class NaayeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationInstance = applicationContext
        setUpDefaultLanguage()
    }

    private fun setUpDefaultLanguage() {
        val langCode = LanguageManager.getLanguageCode()
        val config = resources.configuration
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    companion object {
        private var applicationInstance: Context? = null
        var latitude: Double = -1.0
        var longitude: Double = -1.0
        fun getNaayeeAppContext(): Context? {
            return applicationInstance
        }
    }

}