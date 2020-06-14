package com.development.pega.gastodeenergia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.development.pega.gastodeenergia.listener.EletronicObjectListener
import com.development.pega.gastodeenergia.R
import com.development.pega.gastodeenergia.viewHolder.RecyclerViewHolder
import com.development.pega.gastodeenergia.model.ElectronicObject

class RecyclerAdapter: RecyclerView.Adapter<RecyclerViewHolder>() {

    private var mList: List<ElectronicObject> = arrayListOf()
    private lateinit var mListener: EletronicObjectListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_recycler_view, parent, false)


        return RecyclerViewHolder(
            itemView,
            mListener
        )
    }

    override fun getItemCount(): Int {
        return if(mList != null) mList.count() else 0
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    fun updateList(list: List<ElectronicObject>) {
        mList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: EletronicObjectListener) {
        mListener = listener
    }
}