package com.health.pressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.databinding.ItemSetAlarmBinding

class AlarmListAdapter(
    private val context: Context,
    private val datas: MutableList<AlarmItem>,
    private val onDel: (AlarmItem) -> Unit = {},
    private val onEdit: (AlarmItem) -> Unit = {},
) : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSetAlarmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSetAlarmBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datas[position]
        holder.binding.run {
            itemSwitch.isChecked = item.isOpen
            itemTime.text = item.timeFormat
            itemDel.setOnClickListener {
                onDel.invoke(item)
            }
            itemEdit.setOnClickListener {
                onEdit.invoke(item)
            }
            itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (item.isOpen != isChecked) {
                    item.isOpen = isChecked
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = datas.size
}