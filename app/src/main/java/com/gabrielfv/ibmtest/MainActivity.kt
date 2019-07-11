package com.gabrielfv.ibmtest

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.gabrielfv.ibmtest.libraries.core.CoreActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CoreActivity(R.layout.activity_main) {

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            bottomNavigationBar.updatePosition(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = PagesAdapter(supportFragmentManager, lifecycle, pages)
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)

        bottomNavigationBar.setOnItemSelectedListener { position ->
            viewPager.currentItem = position.ordinal
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    companion object {
        val pages = listOf(
            PagesAdapter.Pages.INVESTMENTS,
            PagesAdapter.Pages.FORM
        )
    }
}