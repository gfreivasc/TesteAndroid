package com.gabrielfv.ibmtest.features.form.di

import com.gabrielfv.ibmtest.features.form.FormContract
import com.gabrielfv.ibmtest.features.form.FormFragment
import com.gabrielfv.ibmtest.features.form.FormPresenter
import com.gabrielfv.ibmtest.libraries.core.di.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FormModule {

    @FeatureScope
    @ContributesAndroidInjector(modules = [Providers::class])
    abstract fun contributesInjector(): FormFragment

    @Module
    class Providers {
        @FeatureScope
        @Provides
        fun providesView(formFragment: FormFragment): FormContract.View = formFragment

        @FeatureScope
        @Provides
        fun providesPresenter(view: FormContract.View): FormContract.Presenter = FormPresenter(view)
    }
}