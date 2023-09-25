package com.health.pressure

import com.health.pressure.dao.Pressure

lateinit var mApp: PressureApp

val wheelData by lazy { (20..300).toMutableList() }

val datas = mutableListOf<Pressure>()