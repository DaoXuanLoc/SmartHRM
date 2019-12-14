package com.ominext.smarthrm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ominext.smarthrm.R
import com.ominext.smarthrm.model.History

class RecyclerViewAdapter(private var historys: ArrayList<History>) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_history, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historys.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindView(historys[position])
    }

    fun setDate(items: ArrayList<History>) {
        historys.addAll(items)
        notifyDataSetChanged()
    }
}