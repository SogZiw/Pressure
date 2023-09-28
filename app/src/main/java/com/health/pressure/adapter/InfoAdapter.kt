package com.health.pressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.basic.InfoData
import com.health.pressure.databinding.ItemInfoBinding
import com.health.pressure.ext.stringValue

class InfoAdapter(private val context: Context, private val data: MutableList<InfoData>, private val onClick: (InfoData) -> Unit = {}) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemInfoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.run {
            itemImg.setImageResource(item.img)
            itemName.text = item.question.stringValue
        }
        holder.itemView.setOnClickListener { onClick.invoke(item) }
    }

    override fun getItemCount(): Int = data.size
}