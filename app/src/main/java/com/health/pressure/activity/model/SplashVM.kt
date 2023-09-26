package com.health.pressure.activity.model

import android.animation.ValueAnimator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.health.pressure.ext.startValueAnimator

class SplashVM : ViewModel() {

    private var animator: ValueAnimator? = null
    val progress = MutableLiveData<Int>()
    val showAd = MutableLiveData<Boolean>()

    fun startAnim(onUpdateValue: (value: Int) -> Unit = {}, onEnd: () -> Unit = {}) {
        animator = 10000L.startValueAnimator(onUpdateValue = {
            progress.postValue(it)
            onUpdateValue.invoke(it)
        }, onEnd = { onEnd.invoke() })
    }

    fun maxAnim(onEnd: () -> Unit = {}) {
        animator?.cancel()
        val curProgress = progress.value ?: 0
        animator = ((100 - curProgress) * 10L).startValueAnimator(curProgress, 100, onUpdateValue = { progress.postValue(it) }, onEnd = { onEnd.invoke() })
    }

}