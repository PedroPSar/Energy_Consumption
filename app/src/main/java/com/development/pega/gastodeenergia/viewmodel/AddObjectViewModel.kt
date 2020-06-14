package com.development.pega.gastodeenergia.viewmodel

import android.app.Application
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

    fun save(id: Int, name: String, watts: Int, usedHours: String) {
        val obj = ElectronicObject(id, name, watts, usedHours)

        if(id == 0) {
            mSaveObject.value = repository.save(obj)
        } else {
            mSaveObject.value = repository.update(obj)
        }
    }

    fun load(id: Int) {
        mElectronicObj.value = repository.getObj(id)
    }

}