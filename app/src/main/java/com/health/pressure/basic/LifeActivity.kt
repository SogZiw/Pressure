package com.health.pressure.basic

import androidx.databinding.ViewDataBinding

abstract class LifeActivity<B : ViewDataBinding> : BaseActivity<B>() {

    var resumed = false

    override fun onStart() {
        super.onStart()
        resumed = false
    }

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onPause() {
        resumed = false
        super.onPause()
    }

    override fun onStop() {
        resumed = false
        super.onStop()
    }

}