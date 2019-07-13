package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.domain.form.EmailValidationUseCase
import com.gabrielfv.ibmtest.domain.form.PhoneValidationUseCase

class FormPresenter(
    private val view: FormContract.View,
    private val emailValidation: EmailValidationUseCase,
    private val phoneValidation: PhoneValidationUseCase
) : FormContract.Presenter {

    override fun start() {

    }

    override fun validateEmail(email: String) {
        val valid = emailValidation(email)
        view.emailValidation(valid)
    }

    override fun validatePhone(phone: String) {
        val unformatted = phone.filter { it.isDigit() }
        val valid = phoneValidation(unformatted)
        view.phoneValidation(valid)
    }
}