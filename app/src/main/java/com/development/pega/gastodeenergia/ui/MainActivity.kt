package com.development.pega.gastodeenergia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.pega.gastodeenergia.viewmodel.MainViewModel
import com.development.pega.gastodeenergia.R
import com.development.pega.gastodeenergia.adapter.RecyclerAdapter
import com.development.pega.gastodeenergia.constants.AppConstants
import com.development.pega.gastodeenergia.listener.EletronicObjectListener
import com.development.pega.gastodeenergia.model.ElectronicObject
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mAdapter: RecyclerAdapter = RecyclerAdapter()
    private lateinit var mViewModel: MainViewModel
    private lateinit var mViewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var mListener: EletronicObjectListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModelFactory = ViewModelProvider.AndroidViewModelFactory(application)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(MainViewModel::class.java)

        button_save_tariff.setOnClickListener(this)

        recycler_view.layoutManager = LinearLayoutManager(this)

        recycler_view.adapter = mAdapter

        fab_add_object.setOnClickListener{
            startActivity(Intent(this, AddObjectActivity::class.java))
        }

        mListener = object : EletronicObjectListener{
            override fun edit(id: Int) {
                val intent = Intent(applicationContext, AddObjectActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(AppConstants.OBJECT_ID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun delete(id: Int) {
                mViewModel.delete(id)
                mViewModel.loadList()
            }

        }

        mAdapter.attachListener(mListener)
        observer()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadList()
        edit_tariff.setText((mViewModel.getEnergyTariff().toString()))
    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_save_tariff) {
            val tariff = edit_tariff.text.toString().toFloat()
            val result = mViewModel.saveEnergyTariff(tariff)
            if(result) {
                Toast.makeText(this, getString(R.string.toast_tariff_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.toast_tariff_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observer() {
        mViewModel.list.observe(this, Observer {
            mAdapter.updateList(it)
            val tariff = mViewModel.getEnergyTariff()
            val paidValue = mViewModel.energyPaidValueCalc(tariff)
            val paidText = applicationContext.getString(R.string.dollar_sign) + "%.2f".format(paidValue)
            text_value.text = paidText
        })

        mViewModel.energyTariff.observe(this, Observer {
            edit_tariff.setText(it.toString())
            val paidValue = mViewModel.energyPaidValueCalc(it)
            val paidText = applicationContext.getString(R.string.dollar_sign) + "%.2f".format(paidValue)
            text_value.text = paidText
        })

        mViewModel.saveResult.observe(this, Observer {
            val tariff = mViewModel.getEnergyTariff()
            val paidValue = mViewModel.energyPaidValueCalc(tariff)
            val paidText = applicationContext.getString(R.string.dollar_sign) + "%.2f".format(paidValue)
            text_value.text = paidText
        })
    }
}
