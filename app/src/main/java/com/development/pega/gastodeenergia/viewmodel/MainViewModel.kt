package com.development.pega.gastodeenergia.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.development.pega.gastodeenergia.control.LimitHours
import com.development.pega.gastodeenergia.database.Preferences
import com.development.pega.gastodeenergia.database.Repository
import com.development.pega.gastodeenergia.model.ElectronicObject

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository.getInstance(application.applicationContext)
    private val preferences = Preferences.getInstance(application.applicationContext)

    private val mList = MutableLiveData<List<ElectronicObject>>()
    val list: LiveData<List<ElectronicObject>> = mList

    private val mEnergyTariff = MutableLiveData<Float>()
    val energyTariff = mEnergyTariff

    private val mSaveResult = MutableLiveData<Boolean>()
    val saveResult = mSaveResult

    fun loadList() {
        mList.value = repository.queryObject()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun energyPaidValueCalc(energyTariff: Float): Float {
        var sum = 0f
        if(list.value != null) {
            val listObj: List<ElectronicObject> = list.value as List<ElectronicObject>
            //hours in minutes
            for(obj in listObj) {
                var minutes = LimitHours.convertHourToMinute(obj.usedHours)
                sum+= (obj.watts * ((minutes * 30f) / 60f)) / 1000f
            }
        }

        return sum * energyTariff
    }

    fun getEnergyTariff(): Float {
        preferences.initSharedPref()
        return preferences.getTariff()
    }

    fun saveEnergyTariff(tariffValue: Float): Boolean {
        preferences.initSharedPref()
        mSaveResult.value = preferences.saveTariff(tariffValue)
        mEnergyTariff.value = tariffValue
        return mSaveResult.value as Boolean
    }

}