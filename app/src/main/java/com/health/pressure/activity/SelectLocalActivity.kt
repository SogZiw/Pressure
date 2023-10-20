package com.health.pressure.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.health.pressure.R
import com.health.pressure.adapter.SelectLocalAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.bean.LocalSelection
import com.health.pressure.basic.bean.LocalState
import com.health.pressure.databinding.ActivitySelectLocalBinding
import com.health.pressure.ext.defLang
import com.health.pressure.ext.goNextPage

class SelectLocalActivity : BaseActivity<ActivitySelectLocalBinding>() {

    override val layoutId: Int get() = R.layout.activity_select_local
    private lateinit var adapter: SelectLocalAdapter
    private val locals by lazy {
        mutableListOf(LocalState.English,
            LocalState.Traditional,
            LocalState.Japanese,
            LocalState.French,
            LocalState.German,
            LocalState.Korean,
            LocalState.Spanish,
            LocalState.Turkish,
            LocalState.Polish,
            LocalState.Italian,
            LocalState.Portuguese,
            LocalState.Thai,
            LocalState.Romanian,
            LocalState.Arabic
        )
    }
    private val fromSet by lazy { intent?.getBooleanExtra("fromSet", false) ?: false }

    override fun initView() {
        binding.btnSure.setOnClickListener {
            defLang = locals.getOrNull(adapter.lastPos)?.languageCode ?: LocalState.English.languageCode
            if (fromSet) goNextPage<MainActivity>(true) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
            else goNextPage<GuideActivity>(true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        val datas = locals.map { LocalSelection(it) }.toMutableList()
        var lastPos = 0
        if (fromSet) datas.onEachIndexed { index, local ->
            if (local.localState.languageCode == defLang) {
                local.selected = true
                lastPos = index
                binding.btnSure.isEnabled = true
            }
        }
        adapter = SelectLocalAdapter(this, datas) { binding.btnSure.isEnabled = true }
        adapter.lastPos = lastPos
        binding.list.run {
            isScrollbarFadingEnabled = false
            itemAnimator = null
        }
        binding.list.adapter = adapter
    }


}