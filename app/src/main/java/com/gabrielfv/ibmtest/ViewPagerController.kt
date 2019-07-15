package com.gabrielfv.ibmtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.gabrielfv.ibmtest.features.form.FormFragment
import com.gabrielfv.ibmtest.libraries.core.ViewPagerStackController

class ViewPagerController(
    supportFragmentManager: FragmentManager,
    parentLifecycle: Lifecycle
) : ViewPagerStackController{
    private val positionStacks = mutableListOf(
        initStack { FormFragment(INVESTMENTS_POSITION, this) },
        initStack { FormFragment(CONTACT_POSITION, this) }
    )

    val adapter = PagesAdapter(
        supportFragmentManager,
        parentLifecycle,
        getCurrentStackTops()
    )

    override fun pushFragment(position: Int, fragment: () -> Fragment) {
        positionStacks[position].add(fragment)
        adapter.updatePages(getCurrentStackTops(), position)
    }

    override fun popFragment(position: Int) {
        positionStacks[position].let { it.removeAt(it.lastIndex) }
        adapter.updatePages(getCurrentStackTops(), position)
    }

    private fun getCurrentStackTops() = positionStacks.map { it.last() }

    private fun initStack(first: () -> Fragment) = mutableListOf(first)

    companion object {
        const val INVESTMENTS_POSITION = 0
        const val CONTACT_POSITION = 1
    }
}