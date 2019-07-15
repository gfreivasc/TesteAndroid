package com.gabrielfv.ibmtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagesAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var pages: List<() -> Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val pendingChanges = mutableListOf<Int>()

    fun updatePages(newPages: List<() -> Fragment>, vararg changedPositions: Int) {
        pages = newPages
        pendingChanges.addAll(changedPositions.toList())
        changedPositions.forEach {
            // notifyDataSetChanged is not doing the trick on the way back here.
            // we're using this hack so it actually gets to switch fragment and reset
            // their state
            notifyItemInserted(it)
            notifyItemMoved(it + 1, it)
        }
    }

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        if (position in pendingChanges) pendingChanges.remove(position)
        return pages[position]()
    }

    override fun getItemId(position: Int): Long {
        if (position in pendingChanges) return RecyclerView.NO_ID
        return super.getItemId(position)
    }
}