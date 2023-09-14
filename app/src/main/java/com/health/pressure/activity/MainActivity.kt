package com.health.pressure.activity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityMainBinding
import com.health.pressure.fragment.HistoryFrag
import com.health.pressure.fragment.SetFrag

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int get() = R.layout.activity_main

    override fun initView() {

        fun iniViewPager() {
            binding.viewPager.run {
                val fragments = listOf(HistoryFrag(), SetFrag())
                isUserInputEnabled = false
                offscreenPageLimit = fragments.size
                adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                    override fun getItemCount() = fragments.size
                    override fun createFragment(position: Int) = fragments[position]
                }
            }
        }
        iniViewPager()
        binding.bottomBar.run {
            setOnApplyWindowInsetsListener(null)
            itemIconTintList = null
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.tab_history -> binding.viewPager.setCurrentItem(0, false)
                    R.id.tab_set -> binding.viewPager.setCurrentItem(1, false)
                }
                return@setOnItemSelectedListener true
            }
        }
    }

}