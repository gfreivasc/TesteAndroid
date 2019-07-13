package com.gabrielfv.ibmtest.domain.di

import com.gabrielfv.ibmtest.domain.form.EmailValidationUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun bindsEmailValidationUseCase() = EmailValidationUseCase()
}