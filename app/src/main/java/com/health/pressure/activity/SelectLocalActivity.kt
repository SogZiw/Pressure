package com.health.pressure.activity

import android.annotation.SuppressLint
import com.health.pressure.R
import com.health.pressure.adapter.SelectLocalAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.bean.LocalSelection
import com.health.pressure.basic.bean.LocalState
import com.health.pressure.databinding.ActivitySelectLocalBinding
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

    override fun initView() {
        binding.btnSure.setOnClickListener {
            goNextPage<GuideActivity>(true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        val datas = locals.map { LocalSelection(it) }.toMutableList().apply {
            firstOrNull()?.selected = true
        }
        adapter = SelectLocalAdapter(this, datas)
        binding.list.run {
            isScrollbarFadingEnabled = false
            scrollBarFadeDuration = 0
            itemAnimator = null
            adapter = adapter
        }
    }


}