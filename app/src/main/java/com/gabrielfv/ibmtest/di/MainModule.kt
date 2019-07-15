package com.gabrielfv.ibmtest.di

import com.gabrielfv.ibmtest.MainActivity
import com.gabrielfv.ibmtest.ViewPagerController
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [Providers::class])
    abstract fun contributesInjector(): MainActivity

    @Module
    class Providers {

        @Provides
        fun providesViewPagerController(activity: MainActivity) = ViewPagerController(
            activity.supportFragmentManager,
            activity.lifecycle
        )
    }
}