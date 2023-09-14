package com.health.pressure.basic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.health.pressure.R
import com.kennyc.bottomsheet.BottomSheetListener
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment

abstract class BaseFrag<B : ViewDataBinding> : Fragment() {

    lateinit var activity: AppCompatActivity
    lateinit var binding: B
    abstract val layoutId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            activity = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    open fun initView() = Unit
    open fun initData() = Unit

    fun showBottomMenu(onSelected: (title: String, id: Int) -> Unit) {
        BottomSheetMenuDialogFragment.Builder(activity)
            .setSheet(R.menu.menu_bottom)
            .setTitle(R.string.time_range)
            .setListener(object : BottomSheetListener {
                override fun onSheetItemSelected(bottomSheet: BottomSheetMenuDialogFragment, item: MenuItem, `object`: Any?) {
                    onSelected.invoke("${item.title ?: ""}", item.itemId)
                }

                override fun onSheetDismissed(bottomSheet: BottomSheetMenuDialogFragment, `object`: Any?, dismissEvent: Int) = Unit
                override fun onSheetShown(bottomSheet: BottomSheetMenuDialogFragment, `object`: Any?) = Unit
            })
            .show(childFragmentManager)
    }

}