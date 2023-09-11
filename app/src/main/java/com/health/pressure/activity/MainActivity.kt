package com.health.pressure.activity

import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int get() = R.layout.activity_main

    override fun initView() {
        binding.bottomBar.run {
            setOnApplyWindowInsetsListener(null)
            itemIconTintList = null
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.tab_task -> {

                    }
                    R.id.tab_history -> {

                    }
                    R.id.tab_info -> {

                    }
                    R.id.tab_set -> {

                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

}