package com.health.pressure.activity

import androidx.activity.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.health.pressure.R
import com.health.pressure.activity.model.MainVM
import com.health.pressure.basic.LifeActivity
import com.health.pressure.databinding.ActivityMainBinding
import com.health.pressure.fragment.HistoryFrag
import com.health.pressure.fragment.HomeFrag
import com.health.pressure.fragment.InfoFrag
import com.health.pressure.fragment.SetFrag

class MainActivity : LifeActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainVM>()
    override val layoutId: Int get() = R.layout.activity_main

    override fun initView() {
        viewModel.changeTab.observe(this) {
            binding.bottomBar.menu.getItem(it).isChecked = true
            binding.viewPager.setCurrentItem(it, false)
        }

        fun iniViewPager() {
            binding.viewPager.run {
                val fragments = listOf(HomeFrag(), HistoryFrag(), InfoFrag(), SetFrag())
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
                    R.id.tab_home -> binding.viewPager.setCurrentItem(0, false)
                    R.id.tab_history -> binding.viewPager.setCurrentItem(1, false)
                    R.id.tab_info -> binding.viewPager.setCurrentItem(2, false)
                    R.id.tab_set -> binding.viewPager.setCurrentItem(3, false)
                }
                return@setOnItemSelectedListener true
            }
        }
    }

}