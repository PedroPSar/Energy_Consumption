package com.development.pega.gastodeenergia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.pega.gastodeenergia.R
import com.development.pega.gastodeenergia.constants.AppConstants
import com.development.pega.gastodeenergia.control.LimitHours
import com.development.pega.gastodeenergia.viewmodel.AddObjectViewModel
import com.github.rtoshiro.util.format.MaskFormatter
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import kotlinx.android.synthetic.main.activity_add_object.*
import kotlinx.android.synthetic.main.row_recycler_view.*

class AddObjectActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var mViewModel: AddObjectViewModel
    private var objectID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_object)

        mViewModelFactory = ViewModelProvider.AndroidViewModelFactory(application)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(AddObjectViewModel::class.java)

        setListeners()
        observer()
        loadData()

        val smf = SimpleMaskFormatter("NN:NN")
        val mtw = MaskTextWatcher(edit_used_hours, smf)
        edit_used_hours.addTextChangedListener(mtw)

    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_save) {


            val name = edit_name.text.toString()
            val watts = edit_watts.text.toString()
            val usedHours = edit_used_hours.text.toString()

            if(mViewModel.save(objectID, name, watts, usedHours)){}
            else Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    fun setListeners() {
        button_save.setOnClickListener(this)
    }

    fun observer() {
        mViewModel.saveObject.observe(this, Observer {
            if(it){
                Toast.makeText(applicationContext, R.string.save_toast_success_text, Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, R.string.save_toast_error_text, Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.electronicObj.observe(this, Observer {
            edit_name.setText(it.name)
            edit_watts.setText(it.watts.toString())
            edit_used_hours.setText(it.usedHours)
        })

        mViewModel.hoursText.observe(this, Observer {
            edit_used_hours.setText(it)
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null) {
            objectID = bundle.getInt(AppConstants.OBJECT_ID)
            mViewModel.load(objectID)
        }
    }
}
