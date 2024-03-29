package com.health.pressure.basic.ad

data class AdItem(
    val id: String,
    val platform: String,
    val type: String,
    val priority: Int,
    val overload: Int,
) {
    fun getTypeFormat(): String {
        return when (type) {
            "op" -> "open"
            "nat" -> "native"
            "ban" -> "banner"
            else -> "interstitial"
        }
    }
}

enum class AdLocation(val placeName: String) {
    OPEN("tk_open"),
    SAVE("tk_save_int"),
    HISTORY("tk_history_nat"),
    ALARM("tk_alarm_nat"),
    TAB("tk_tab_int"),
    BAN("tk_main_ban")
}

typealias onLoaded = (success: Boolean, msg: String?) -> Unit