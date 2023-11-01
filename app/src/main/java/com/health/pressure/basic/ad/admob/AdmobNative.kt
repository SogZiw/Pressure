package com.health.pressure.basic.ad.admob

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdItem
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.onLoaded
import com.health.pressure.databinding.ViewAdmobNativeBinding
import com.health.pressure.databinding.ViewAdmobNativeSmallBinding
import com.health.pressure.ext.corner
import com.health.pressure.ext.logcat
import com.health.pressure.mApp

class AdmobNative(val adLoc: AdLocation, val item: AdItem) : BaseAd(adLoc, item) {

    private var native: NativeAd? = null
    private val adRequest get() = AdRequest.Builder().build()

    override fun loadAd(context: Context, onLoaded: onLoaded) {
        "${adLoc.placeName} ${item.type} - ${item.id} start load ad".logcat()
        AdLoader.Builder(mApp, item.id).apply {
            forNativeAd { ad ->
                native = ad
                ad.setOnPaidEventListener { onAdRevenue(it, ad.responseInfo) }
                onLoaded.invoke(true, "")
            }
            withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(e: LoadAdError) = onLoaded.invoke(false, e.message)
                override fun onAdClicked() = AdInstance.addClick()
            })
            withNativeAdOptions(NativeAdOptions.Builder().apply {
                setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_LEFT)
            }.build())

        }.build().loadAd(adRequest)
    }

    fun show(context: Context, parent: ViewGroup) {
        if (null == native) return
        val binding = ViewAdmobNativeBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.nativeAdView.run {
            iconView = binding.icon.apply { setImageDrawable(native?.icon?.drawable) }
            mediaView = binding.media.apply { mediaContent = native?.mediaContent }
            mediaView?.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            mediaView?.corner(8)
            headlineView = binding.appTitle.apply { text = native?.headline ?: "" }
            bodyView = binding.appDesc.apply { text = native?.body ?: "" }
            callToActionView = binding.actionView.apply { text = native?.callToAction ?: "" }
            native?.let { setNativeAd(it) }
        }
        parent.removeAllViews()
        parent.addView(binding.root)
        AdInstance.addShow()
        "${adLoc.placeName} ${item.type} - ${item.id} show success".logcat()
    }

    fun showSmall(context: Context, parent: ViewGroup) {
        if (null == native) return
        val binding = ViewAdmobNativeSmallBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.nativeAdView.run {
            iconView = binding.icon.apply { setImageDrawable(native?.icon?.drawable) }
            mediaView = binding.media.apply { mediaContent = native?.mediaContent }
            mediaView?.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            mediaView?.corner(8)
            headlineView = binding.appTitle.apply { text = native?.headline ?: "" }
            bodyView = binding.appDesc.apply { text = native?.body ?: "" }
            callToActionView = binding.actionView.apply { text = native?.callToAction ?: "" }
            native?.let { setNativeAd(it) }
        }
        parent.removeAllViews()
        parent.addView(binding.root)
        AdInstance.addShow()
        "${adLoc.placeName} ${item.type} - ${item.id} show success".logcat()
    }

    override fun destroy() {
        native?.destroy()
        native = null
    }


}