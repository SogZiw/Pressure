package com.health.pressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.databinding.ItemGuideLandBinding

class GuideAdapter(private val context: Context, private val data: MutableList<Int>) : RecyclerView.Adapter<GuideAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGuideLandBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGuideLandBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemImg.setImageResource(data[position])
    }

    override fun getItemCount(): Int = data.size


}