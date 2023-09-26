package com.health.pressure.data

data class AdItem(
    val id: String,
    val platform: String,
    val type: String,
    val priority: Int,
    val overload: Int,
)