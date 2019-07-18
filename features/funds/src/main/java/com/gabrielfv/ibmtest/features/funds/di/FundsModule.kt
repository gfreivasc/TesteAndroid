package com.gabrielfv.ibmtest.features.funds.di

import com.gabrielfv.ibmtest.domain.funds.GetFundUseCase
import com.gabrielfv.ibmtest.domain.funds.external.FundProvider
import com.gabrielfv.ibmtest.features.funds.FundsContract
import com.gabrielfv.ibmtest.features.funds.FundsFragment
import com.gabrielfv.ibmtest.features.funds.FundsPresenter
import com.gabrielfv.ibmtest.features.funds.data.FundApi
import com.gabrielfv.ibmtest.features.funds.data.FundRepository
import com.gabrielfv.ibmtest.libraries.core.di.FeatureScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

@Module
abstract class FundsModule {

    @ContributesAndroidInjector(modules = [Providers::class])
    abstract fun contributesInjector(): FundsFragment

    @Module
    class Providers {

        @FeatureScope
        @Provides
        fun providesFundsView(fragment: FundsFragment): FundsContract.View = fragment

        @FeatureScope
        @Provides
        fun providesFundApi(retrofit: Retrofit): FundApi = retrofit.create(FundApi::class.java)

        @FeatureScope
        @Provides
        fun providesFundProvider(fundApi: FundApi): FundProvider = FundRepository(fundApi)

        @FeatureScope
        @Provides
        fun providesFundsPresenter(
            view: FundsContract.View,
            getFundUseCase: GetFundUseCase
        ): FundsContract.Presenter = FundsPresenter(view, getFundUseCase)
    }
}