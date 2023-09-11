package com.health.pressure.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.health.pressure.ext.autoDensity
import com.health.pressure.ext.fullScreenMode

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var activity: BaseActivity<*>
    lateinit var binding: B
    abstract val layoutId: Int

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.fullScreenMode()
        autoDensity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

}