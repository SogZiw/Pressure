package com.health.pressure.basic

import com.health.pressure.R

enum class InfoData(val question: Int, val desc: Int, val source: String, val img: Int) {
    Q1(R.string.info_1_q, R.string.info_1, "https://medlineplus.gov/highbloodpressure.html", R.drawable.ic_info_1),
    Q2(R.string.info_2_q, R.string.info_2, "https://medlineplus.gov/highbloodpressure.html", R.drawable.ic_info_2),
    Q3(R.string.info_3_q, R.string.info_3, "https://medlineplus.gov/highbloodpressure.html", R.drawable.ic_info_3),
    Q4(R.string.info_4_q, R.string.info_4, "https://medlineplus.gov/highbloodpressure.html", R.drawable.ic_info_4),
    Q5(R.string.info_5_q, R.string.info_5, "https://medlineplus.gov/howtopreventhighbloodpressure.html", R.drawable.ic_info_1),
    Q6(R.string.info_6_q, R.string.info_6, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_2),
    Q7(R.string.info_7_q, R.string.info_7, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_3),
    Q8(R.string.info_8_q, R.string.info_8, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_4),
    Q9(R.string.info_9_q, R.string.info_9, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_1),
    Q10(R.string.info_10_q, R.string.info_10, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_2),
    Q11(R.string.info_11_q, R.string.info_11, "https://medlineplus.gov/highbloodpressureinpregnancy.html", R.drawable.ic_info_3),
    Q12(R.string.info_12_q, R.string.info_12, "https://medlineplus.gov/dasheatingplan.html", R.drawable.ic_info_4),
}