package com.health.pressure.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityWebviewBinding

class WebviewActivity : BaseActivity<ActivityWebviewBinding>() {

    companion object {
        fun goWebView(context: Context, webUrl: String) {
            context.startActivity(Intent(context, WebviewActivity::class.java).apply {
                putExtra("webview_url", webUrl)
            })
        }
    }

    override val layoutId: Int get() = R.layout.activity_webview

    private val webUrl by lazy { intent?.getStringExtra("webview_url") }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = WebViewClient()
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                binding.title.text = title ?: ""
                super.onReceivedTitle(view, title)
            }
        }
        binding.webview.loadUrl(webUrl ?: "about:blank")
    }


}