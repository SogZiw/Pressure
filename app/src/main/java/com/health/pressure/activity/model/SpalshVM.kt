package com.health.pressure.activity.model

import android.animation.ValueAnimator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.health.pressure.ext.startValueAnimator

class SpalshVM : ViewModel() {

    val progress = MutableLiveData<Int>()
    private var animator: ValueAnimator? = null

    fun startAnim(onEnd: () -> Unit = {}) {
        animator = 3000L.startValueAnimator(onUpdateValue = { progress.postValue(it) }, onEnd = { onEnd.invoke() })
    }

}