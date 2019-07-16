package com.gabrielfv.ibmtest.libraries.core

import androidx.fragment.app.Fragment

interface ViewPagerStackController {

    fun pushFragment(fragment: () -> Fragment)

    fun popFragment()
}