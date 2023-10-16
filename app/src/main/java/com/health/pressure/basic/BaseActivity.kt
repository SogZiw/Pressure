package com.health.pressure.basic

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.health.pressure.R
import com.health.pressure.ext.autoDensity
import com.health.pressure.ext.defLang
import com.health.pressure.ext.fullScreenMode
import com.health.pressure.ext.updateLocalConf
import java.util.*

@Suppress("DEPRECATION")
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var activity: BaseActivity<*>
    lateinit var binding: B
    abstract val layoutId: Int

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.updateLocalConf(defLang))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.fullScreenMode()
        autoDensity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        binding = DataBindingUtil.setContentView(this, layoutId)
        doOnClickBack()
        initView()
        initData()
    }

    private fun doOnClickBack() {
        findViewById<AppCompatImageView>(R.id.btnBack)?.setOnClickListener { onBackPressed() }
    }

    open fun initView() = Unit
    open fun initData() = Unit

}