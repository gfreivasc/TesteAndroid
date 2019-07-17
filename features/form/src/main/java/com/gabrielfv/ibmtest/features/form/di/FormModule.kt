package com.gabrielfv.ibmtest.features.form.di

import com.gabrielfv.ibmtest.domain.form.FetchCellsUseCase
import com.gabrielfv.ibmtest.domain.form.ValidateEmailUseCase
import com.gabrielfv.ibmtest.domain.form.ValidateFormUseCase
import com.gabrielfv.ibmtest.domain.form.ValidatePhoneUseCase
import com.gabrielfv.ibmtest.domain.form.external.CellsProvider
import com.gabrielfv.ibmtest.features.form.FormContract
import com.gabrielfv.ibmtest.features.form.FormFragment
import com.gabrielfv.ibmtest.features.form.FormPresenter
import com.gabrielfv.ibmtest.features.form.data.CellsApi
import com.gabrielfv.ibmtest.features.form.data.CellsRepository
import com.gabrielfv.ibmtest.libraries.core.di.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

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
        fun providesCellsApi(retrofit: Retrofit): CellsApi = retrofit.create(CellsApi::class.java)

        @FeatureScope
        @Provides
        fun providesCellProvider(cellsApi: CellsApi): CellsProvider = CellsRepository(cellsApi)

        @FeatureScope
        @Provides
        fun providesPresenter(
            view: FormContract.View,
            fetchCellsUseCase: FetchCellsUseCase,
            validateEmailUseCase: ValidateEmailUseCase,
            validatePhoneUseCase: ValidatePhoneUseCase,
            validateFormUseCase: ValidateFormUseCase
        ): FormContract.Presenter = FormPresenter(
            view, fetchCellsUseCase, validateEmailUseCase, validatePhoneUseCase, validateFormUseCase
        )
    }
}