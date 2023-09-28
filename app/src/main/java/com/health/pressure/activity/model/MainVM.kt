package com.health.pressure.activity.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM : ViewModel() {

    val changeTab = MutableLiveData<Int>()

}