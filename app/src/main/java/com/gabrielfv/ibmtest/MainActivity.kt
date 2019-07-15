package com.gabrielfv.ibmtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gabrielfv.ibmtest.features.form.FormFragment
import com.gabrielfv.ibmtest.libraries.core.CoreActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : CoreActivity(R.layout.activity_main) {
    @Inject
    lateinit var viewPagerController: ViewPagerController

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            bottomNavigationBar.updatePosition(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = viewPagerController.adapter
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)

        bottomNavigationBar.setOnItemSelectedListener { position ->
            viewPager.currentItem = position.ordinal
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}