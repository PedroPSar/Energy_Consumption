package com.development.pega.gastodeenergia.database

import android.content.Context
import android.content.SharedPreferences
import com.development.pega.gastodeenergia.constants.AppConstants
import java.lang.Exception

class Preferences(context: Context){

    private lateinit var sharedPref: SharedPreferences
    private val mContext = context

    companion object {
        private lateinit var preferences: Preferences

        fun getInstance(context: Context): Preferences {
            if(!::preferences.isInitialized) {
                preferences = Preferences(context)
            }
            return preferences
        }
    }

    fun initSharedPref() {
        if(!::sharedPref.isInitialized) {
            sharedPref = mContext.getSharedPreferences(AppConstants.ENERGY_TARIFF_KEY, Context.MODE_PRIVATE)
        }
    }

    fun saveTariff(energyTariff: Float): Boolean {
        return try {
            sharedPref.edit().putFloat(AppConstants.ENERGY_TARIFF_KEY, energyTariff).apply()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getTariff(): Float {
        return sharedPref.getFloat(AppConstants.ENERGY_TARIFF_KEY, AppConstants.ENERGY_TARIFF_DEFAULT_VALUE)
    }
}