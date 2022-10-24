package com.naayee.naayeeclient.codes.utility

import android.content.Context
import android.os.Build
import com.naayee.naayeeclient.codes.authConfig.AuthConfigManager
import com.naayee.naayeeclient.codes.NaayeeApplication
import com.naayee.naayeeclient.codes.factory.ApiModule.Companion.SHARED_PREF
import com.naayee.naayeeclient.codes.model.LanguageDataClass
import java.util.*

const val ENGLISH = "English"
const val HINDI = "Hindi"
const val EN_CODE = "en"
const val HI_CODE = "hi"


object LanguageManager {
    private val sharedPref by lazy { NaayeeApplication.getNaayeeAppContext()?.getSharedPreferences(
        SHARED_PREF, Context.MODE_PRIVATE) }
    private val langName: String = "langName"
    private val langCode: String = "langCode"
    private val doesShownLanguageSettingFirstTime = "doesShownLangSettingFirstTime"

    fun setLanguageInfo(languageDataClass: LanguageDataClass, callback: (Boolean) -> Unit) {
        sharedPref?.edit()?.putString(langName, languageDataClass.languageName)?.apply()
        sharedPref?.edit()?.putString(langCode, languageDataClass.langCode)?.apply()
        callback.invoke(true)
    }

    fun getLanguageCode(): String {
        return sharedPref?.getString(langCode, EN_CODE) ?: EN_CODE
    }

    fun getLanguageName(): String {
        return sharedPref?.getString(langCode, ENGLISH) ?: ENGLISH
    }

    fun setDoesShownLanguageSettingFirstTime(isShown: Boolean) {
        sharedPref?.edit()?.putBoolean(doesShownLanguageSettingFirstTime, isShown)?.apply()
    }

    fun getShownLangSettingFirstTime(): Boolean {
        return sharedPref?.getBoolean(doesShownLanguageSettingFirstTime, false) ?: false
    }

    fun getStringInfo(int: Int):String {
        return NaayeeApplication.getNaayeeAppContext()?.getString(int)?:""
    }
    fun setUpLanguage(languageCode: String,context: Context) {
        val config = context.resources.configuration
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}