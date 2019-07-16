package com.gabrielfv.ibmtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.gabrielfv.ibmtest.features.form.FormFragment
import com.gabrielfv.ibmtest.libraries.core.ViewPagerStackController

class ViewPagerController(
    supportFragmentManager: FragmentManager,
    parentLifecycle: Lifecycle
) {
    private val positionStacks = mutableListOf(
        initStack { FormFragment(StackController(INVESTMENTS_POSITION)) },
        initStack { FormFragment(StackController(CONTACT_POSITION)) }
    )

    val adapter = PagesAdapter(
        supportFragmentManager,
        parentLifecycle,
        getCurrentStackTops()
    )

    companion object {
        const val INVESTMENTS_POSITION = 0
        const val CONTACT_POSITION = 1
    }

    private fun getCurrentStackTops() = positionStacks.map { it.last() }

    private fun initStack(first: () -> Fragment) = mutableListOf(first)

    inner class StackController(private val position: Int) : ViewPagerStackController {
        override fun pushFragment(fragment: () -> Fragment) {
            positionStacks[position].add(fragment)
            adapter.updatePages(getCurrentStackTops(), position)
        }

        override fun popFragment() {
            positionStacks[position].let { it.removeAt(it.lastIndex) }
            adapter.updatePages(getCurrentStackTops(), position)
        }
    }
}