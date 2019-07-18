package com.gabrielfv.ibmtest.di

import android.content.Context
import com.gabrielfv.ibmtest.Application
import com.gabrielfv.ibmtest.features.form.di.FormModule
import com.gabrielfv.ibmtest.features.funds.di.FundsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    MainModule::class,
    FormModule::class,
    FundsModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<Application> {

    @Component.Factory
    interface Factory {
        fun appContext(@BindsInstance app: Context): AppComponent
    }
}