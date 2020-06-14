package com.development.pega.gastodeenergia.viewHolder

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.development.pega.gastodeenergia.R
import com.development.pega.gastodeenergia.listener.EletronicObjectListener
import com.development.pega.gastodeenergia.model.ElectronicObject

class RecyclerViewHolder(itemView: View, private val listener: EletronicObjectListener): RecyclerView.ViewHolder(itemView) {

    fun bind(eletronicObj: ElectronicObject) {
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        val textWatts = itemView.findViewById<TextView>(R.id.text_watts)
        val textUsedHours = itemView.findViewById<TextView>(R.id.used_hours)
        val btnEdit = itemView.findViewById<Button>(R.id.button_edit)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.button_delete)

        val watts = eletronicObj.watts.toString() + itemView.context.getString(R.string.row_watts)
        val usedHoursDay = eletronicObj.usedHours + itemView.context.getString(R.string.row_per_day)

        textName.text = eletronicObj.name
        textWatts.text = watts
        textUsedHours.text = usedHoursDay

        btnEdit.setOnClickListener{
            listener.edit(eletronicObj.id)
        }

        btnDelete.setOnClickListener{
            AlertDialog.Builder(itemView.context)
                .setTitle(itemView.context.getString(R.string.dialog_delete_title))
                .setMessage(itemView.context.getString(R.string.dialog_delete_message))
                .setPositiveButton(R.string.dialog_delete_positive_button) { dialog, which -> listener.delete(eletronicObj.id) }
                .setNegativeButton(R.string.dialog_delete_negative_button, null).show()
        }
    }
}