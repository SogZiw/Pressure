package com.health.pressure.activity

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.method.ScrollingMovementMethod
import com.health.pressure.Constants
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.InfoData
import com.health.pressure.databinding.ActivityInfoDetailBinding
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.stringValue

@Suppress("DEPRECATION")
class InfoDetailActivity : BaseActivity<ActivityInfoDetailBinding>() {

    private val infoData by lazy { intent?.getSerializableExtra("InfoData") as? InfoData }
    override val layoutId: Int get() = R.layout.activity_info_detail

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.info.movementMethod = ScrollingMovementMethod.getInstance()
        binding.source.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        infoData?.let { info ->
            binding.layoutQues.itemName.text = info.question.stringValue
            binding.layoutQues.itemImg.setImageResource(info.img)
            binding.info.text = info.desc.stringValue
            binding.source.text = "${R.string.source.stringValue}:${info.source}"
            binding.source.setOnClickListener {
                goNextPage<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, info.source) }
            }
        }
    }

}