package com.health.pressure.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.databinding.ItemSetAlarmBinding

class AlarmListAdapter(
    private val context: Context,
    val datas: MutableList<AlarmItem>,
    private val onDel: (AlarmItem) -> Unit = {},
    private val onEdit: (AlarmItem) -> Unit = {},
    private val onChangeOpen: (AlarmItem) -> Unit = {},
) : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSetAlarmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSetAlarmBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datas[position]
        holder.binding.run {
            itemSwitch.isChecked = item.isOpen
            itemTime.text = item.timeFormat
            itemDel.setOnClickListener {
                onDel.invoke(item)
            }
            itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (item.isOpen != isChecked) {
                    item.isOpen = isChecked
                    onChangeOpen.invoke(item)
                }
            }
        }
        holder.itemView.setOnClickListener {
            onEdit.invoke(item)
        }
    }

    override fun getItemCount(): Int = datas.size
}