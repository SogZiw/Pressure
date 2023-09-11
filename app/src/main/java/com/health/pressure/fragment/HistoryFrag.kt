package com.health.pressure.fragment

import android.content.Intent
import com.health.pressure.R
import com.health.pressure.activity.RecordActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragHistoryBinding

class HistoryFrag : BaseFrag<FragHistoryBinding>() {

    override val layoutId: Int get() = R.layout.frag_history

    override fun initView() {
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }
    }

}