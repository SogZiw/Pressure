package com.health.pressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.basic.bean.LocalSelection
import com.health.pressure.databinding.ItemLocalBinding

class SelectLocalAdapter(private val context: Context, val datas: MutableList<LocalSelection>, private val onClick: () -> Unit = {}) :
    RecyclerView.Adapter<SelectLocalAdapter.ViewHolder>() {

    var lastPos = 0

    class ViewHolder(val binding: ItemLocalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLocalBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datas[position]
        holder.binding.itemName.text = item.localState.localName
        holder.binding.itemSwitch.isChecked = item.selected
        holder.binding.line.isVisible = position != datas.lastIndex
        holder.itemView.setOnClickListener {
            val curPos = holder.adapterPosition
            datas.getOrNull(lastPos)?.selected = false
            item.selected = true
            notifyItemChanged(lastPos)
            notifyItemChanged(curPos)
            lastPos = holder.adapterPosition
            onClick.invoke()
        }
    }

    override fun getItemCount(): Int = datas.size

}