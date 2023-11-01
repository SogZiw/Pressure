package com.health.pressure

import com.health.pressure.dao.Pressure

lateinit var mApp: PressureApp
val isDebug by lazy { BuildConfig.DEBUG }

val wheelData by lazy { (20..300).toMutableList() }

val datas = mutableListOf<Pressure>()


object Constants {
    const val WEBVIEW_URL = "webview_url"
    const val PRIVACY_POLICY = "https://www.baidu.com"
    const val USER_AGREE = "https://www.baidu.com"

    val AD_JSON = """
        {
          "hskkhs": 60,
          "jakh": 15,
          "tk_open": [
            {
              "lost": "ca-app-pub-3940256099942544/3419835294",
              "dream": "admob",
              "todo": "op",
              "ppaa": 13800,
              "ashi": 2
            },
            {
              "lost": "ca-app-pub-3940256099942544/8691691433",
              "dream": "admob",
              "todo": "int",
              "ppaa": 3000,
              "ashi": 1
            }
          ],
          "tk_save_int": [
            {
              "lost": "ca-app-pub-3940256099942544/8691691433",
              "dream": "admob",
              "todo": "int",
              "ppaa": 3000,
              "ashi": 2
            },
            {
              "lost": "ca-app-pub-3940256099942544/1033173712",
              "dream": "admob",
              "todo": "int",
              "ppaa": 3000,
              "ashi": 1
            }
          ],
          "tk_tab_int": [
            {
              "lost": "ca-app-pub-3940256099942544/8691691433",
              "dream": "admob",
              "todo": "int",
              "ppaa": 3000,
              "ashi": 2
            },
            {
              "lost": "ca-app-pub-3940256099942544/1033173712",
              "dream": "admob",
              "todo": "int",
              "ppaa": 3000,
              "ashi": 1
            }
          ],
          "tk_history_nat": [
            {
              "lost": "ca-app-pub-3940256099942544/1044960115",
              "dream": "admob",
              "todo": "nat",
              "ppaa": 3000,
              "ashi": 2
            },
            {
              "lost": "ca-app-pub-3940256099942544/2247696110",
              "dream": "admob",
              "todo": "nat",
              "ppaa": 3000,
              "ashi": 1
            }
          ],
          "tk_alarm_nat": [
            {
              "lost": "ca-app-pub-3940256099942544/1044960115",
              "dream": "admob",
              "todo": "nat",
              "ppaa": 3000,
              "ashi": 2
            },
            {
              "lost": "ca-app-pub-3940256099942544/2247696110",
              "dream": "admob",
              "todo": "nat",
              "ppaa": 3000,
              "ashi": 1
            }
          ],
          "tk_main_ban": [
            {
              "lost": "ca-app-pub-3940256099942544/6300978111",
              "dream": "admob",
              "todo": "ban",
              "ppaa": 3000,
              "ashi": 3
            }
          ]
        }
    """.trimIndent()
}