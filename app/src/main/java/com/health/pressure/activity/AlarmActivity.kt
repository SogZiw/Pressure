package com.health.pressure.activity

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.adapter.AlarmListAdapter
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.ActivityAlarmBinding
import com.health.pressure.ext.alarmInfo
import com.health.pressure.ext.createCommonDialog
import com.health.pressure.ext.formatTime
import com.health.pressure.ext.hhmmPattern
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : LifeActivity<ActivityAlarmBinding>() {

    override val layoutId: Int get() = R.layout.activity_alarm
    private lateinit var adapter: AlarmListAdapter
    private var nativeAd: BaseAd? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        binding.btnAdd.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.plz_set_alarm))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setDefaultTime(System.currentTimeMillis())
                .setDisplayType(DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.add)) { milliseconds ->
                    val format = milliseconds.formatTime(hhmmPattern)
                    if (adapter.datas.any { it.timeFormat == format }) {
                        adapter.datas.removeIf { it.timeFormat == format }
                    }
                    adapter.datas.add(AlarmItem(format))
                    adapter.notifyDataSetChanged()
                    alarmInfo = adapter.datas
                    AdInstance.saveAd.showFullScreenIfGoodSwitchOn(this)
                }
                .setOnCancel(text = getString(R.string.cancel))
                .build().show()
        }
    }

    override fun initData() {
        initAdapter()
        showNative()
        EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to AdLocation.ALARM.placeName))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        adapter = AlarmListAdapter(this, ArrayList(alarmInfo), onDel = {
            createCommonDialog(msg = getString(R.string.sure_to_del), onPositive = {
                adapter.datas.remove(it)
                adapter.notifyDataSetChanged()
                alarmInfo = adapter.datas
            })
        }, onEdit = { item ->
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.change_alarm))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setDefaultTime(SimpleDateFormat(hhmmPattern, Locale.ENGLISH).parse(item.timeFormat)?.time ?: System.currentTimeMillis())
                .setDisplayType(DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.sure)) { milliseconds ->
                    val format = milliseconds.formatTime(hhmmPattern)
                    if (adapter.datas.any { it.timeFormat == format }) {
                        adapter.datas.removeIf { it.timeFormat == format }
                    }
                    item.timeFormat = format
                    adapter.notifyDataSetChanged()
                    alarmInfo = adapter.datas
                }
                .setOnCancel(text = getString(R.string.cancel))
                .build().show()
        }, onChangeOpen = {
            alarmInfo = adapter.datas
        })
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

    private fun showNative() {
        AdInstance.alarmAd.keepLoader(this) {
            if (it) {
                lifecycleScope.launch {
                    while (!resumed) delay(500L)
                    AdInstance.alarmAd.showNativeAd(activity, binding.nativeView, true) { baseAd -> nativeAd = baseAd }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }

    override fun onBackPressed() {
        AdInstance.tabAd.showFullScreenIfCan(activity) { finish() }
    }

}