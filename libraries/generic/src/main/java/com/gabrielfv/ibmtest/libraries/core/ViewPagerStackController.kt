package com.gabrielfv.ibmtest.libraries.core

import androidx.fragment.app.Fragment

interface ViewPagerStackController {

    fun pushFragment(position: Int, fragment: () -> Fragment)

    fun popFragment(position: Int)
}