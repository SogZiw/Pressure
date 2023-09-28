package com.health.pressure.basic

import com.health.pressure.R

enum class InfoData(val question: Int, val desc: Int, val source: String) {
    Q1(R.string.info_1_q, R.string.info_1, "https://medlineplus.gov/highbloodpressure.html"),
    Q2(R.string.info_2_q, R.string.info_2, "https://medlineplus.gov/highbloodpressure.html"),
    Q3(R.string.info_3_q, R.string.info_3, "https://medlineplus.gov/highbloodpressure.html"),
    Q4(R.string.info_4_q, R.string.info_4, "https://medlineplus.gov/highbloodpressure.html"),
    Q5(R.string.info_5_q, R.string.info_5, "https://medlineplus.gov/howtopreventhighbloodpressure.html"),
    Q6(R.string.info_6_q, R.string.info_6, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q7(R.string.info_7_q, R.string.info_7, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q8(R.string.info_8_q, R.string.info_8, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q9(R.string.info_9_q, R.string.info_9, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q10(R.string.info_10_q, R.string.info_10, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q11(R.string.info_11_q, R.string.info_11, "https://medlineplus.gov/highbloodpressureinpregnancy.html"),
    Q12(R.string.info_12_q, R.string.info_12, "https://medlineplus.gov/dasheatingplan.html"),
}