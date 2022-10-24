package com.naayee.naayeeclient.codes.utility

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import com.naayee.naayeeclient.codes.NaayeeApplication
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object AndroidDeviceUtils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(): String {
        var deviceId: String = UNDEFINED
        try {
            deviceId = Settings.Secure.getString(
                NaayeeApplication.getNaayeeAppContext()?.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (ex: Exception) {
        }
        return deviceId;
    }

    fun getAndroidVersion(): String {
        var androidVersion: String = UNDEFINED
        try {
            androidVersion = Build.VERSION.SDK_INT.toString()
        } catch (ex: Exception) {

        }
        return androidVersion;
    }

    fun getLocalIpAddress(): String {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddress = intf.inetAddresses
                while (enumIpAddress.hasMoreElements()) {
                    val inetAddress = enumIpAddress.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        if (inetAddress.address == null) {
                            return UNDEFINED
                        }
                        return inetAddress.hostAddress
                    }
                }
            }
        } catch (ex: SocketException) {
            return UNDEFINED
        }
        return UNDEFINED
    }

}

const val UNDEFINED = "com.naayee.naayeeclient.codes.utility.UNDEFINED"