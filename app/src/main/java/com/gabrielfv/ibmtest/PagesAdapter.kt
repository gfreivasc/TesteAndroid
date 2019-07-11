package com.gabrielfv.ibmtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gabrielfv.ibmtest.features.form.FormFragment

class PagesAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val pages: List<Pages>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        return when (pages[position]) {
            Pages.FORM -> FormFragment()
            else -> FormFragment()
        }
    }

    enum class Pages {
        INVESTMENTS,
        FORM
    }
}