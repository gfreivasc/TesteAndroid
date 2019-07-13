package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.domain.form.EmailValidationUseCase

class FormPresenter(
    private val view: FormContract.View,
    private val emailValidation: EmailValidationUseCase
) : FormContract.Presenter {

    override fun start() {

    }

    override fun validateEmail(email: String) {
        val params = EmailValidationUseCase.Params(email)
        val valid = emailValidation(params)
        view.emailValidation(valid)
    }
}