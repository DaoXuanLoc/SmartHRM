package com.ominext.smarthrm.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ominext.smarthrm.R
import com.ominext.smarthrm.model.History

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(history: History){
        var tvDate = itemView.findViewById<TextView>(R.id.tv_adapter_time)
        var tvCheckIn = itemView.findViewById<TextView>(R.id.tv_adapter_checkIn)
        var tvCheckOut = itemView.findViewById<TextView>(R.id.tv_adapter_checkOut)
        tvDate.text = history.date
        tvCheckIn.text= history.checkIn
        tvCheckOut.text = history.checkOut
    }
}