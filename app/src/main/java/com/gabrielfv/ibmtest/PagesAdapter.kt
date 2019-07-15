package com.gabrielfv.ibmtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagesAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var pages: List<() -> Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    fun updatePages(newPages: List<() -> Fragment>, vararg changedPositions: Int) {
        pages = newPages
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        return pages[position]()
    }
}