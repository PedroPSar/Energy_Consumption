package com.development.pega.gastodeenergia.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.development.pega.gastodeenergia.control.LimitHours
import com.development.pega.gastodeenergia.database.Repository
import com.development.pega.gastodeenergia.model.ElectronicObject

class AddObjectViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository = Repository.getInstance(application.applicationContext)

    private val mSaveObject = MutableLiveData<Boolean>()
    val saveObject: LiveData<Boolean> = mSaveObject

    private val mElectronicObj = MutableLiveData<ElectronicObject>()
    val electronicObj = mElectronicObj

    private val mHoursText = MutableLiveData<String>()
    val hoursText = mHoursText

    fun save(id: Int, name: String, watts: String, usedHours: String): Boolean {

        val pattern = "^(([0-1][0-9]|2[0-3]):[0-5][0-9])|(24:00)$".toRegex()

        return if(!name.isEmpty() && !watts.isEmpty() && !usedHours.isEmpty() && pattern.containsMatchIn(usedHours)) {
            val obj = ElectronicObject(id, name, watts.toInt(), usedHours)
            if(id == 0) {
                mSaveObject.value = repository.save(obj)
            } else {
                mSaveObject.value = repository.update(obj)
            }
            true
        }else {
            false
        }
    }

    fun load(id: Int) {
        mElectronicObj.value = repository.getObj(id)
    }

}