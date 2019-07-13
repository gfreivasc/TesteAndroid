package com.gabrielfv.ibmtest

import android.app.Application
import com.gabrielfv.ibmtest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class Application : Application(), HasAndroidInjector {
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory()
            .appContext(this)
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}