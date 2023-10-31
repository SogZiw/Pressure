package com.health.pressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.dao.Pressure
import com.health.pressure.databinding.ItemPressureBinding
import com.health.pressure.ext.getFormatUnit

class PressureHistoryAdapter(private val context: Context, private val data: MutableList<Pressure>, private val onClick: (Pressure) -> Unit = {}) :
    RecyclerView.Adapter<PressureHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPressureBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPressureBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val state = item.state
        holder.binding.run {
            itemBeat.setImageResource(state.beatIcon)
            itemSysValue.text = item.sys.getFormatUnit()
            itemDiaValue.text = item.dia.getFormatUnit()
            itemState.text = state.stateName
            itemTime.text = item.format_time
        }
        holder.itemView.setOnClickListener { onClick.invoke(item) }
    }

    override fun getItemCount(): Int = data.size
}