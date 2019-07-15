package com.gabrielfv.ibmtest.libraries.core

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat

abstract class CoreActivity(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        val systemApiLevel = Build.VERSION.SDK_INT

        if (systemApiLevel >= Build.VERSION_CODES.LOLLIPOP) {
            setUpLollipop()
        }

        if (systemApiLevel >= Build.VERSION_CODES.M) {
            setUpMarshmallow()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setUpLollipop() {
        window.apply {
            navigationBarColor = ResourcesCompat.getColor(resources, R.color.colorAccent, theme)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setUpMarshmallow() {
        window.apply {
            decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = ResourcesCompat.getColor(resources, R.color.offWhite, theme)
        }
    }
}
