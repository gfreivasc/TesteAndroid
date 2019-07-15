package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.domain.form.ValidateEmailUseCase
import com.gabrielfv.ibmtest.domain.form.ValidateFormUseCase
import com.gabrielfv.ibmtest.domain.form.ValidatePhoneUseCase
import com.gabrielfv.ibmtest.domain.form.model.Form

class FormPresenter(
    private val view: FormContract.View,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateFormUseCase: ValidateFormUseCase
) : FormContract.Presenter {

    override fun start() {

    }

    override fun validateEmail(email: String) {
        val valid = validateEmailUseCase(email)
        view.emailValidation(valid)
    }

    override fun validatePhone(phone: String) {
        val unformatted = phone.filter { it.isDigit() }
        val valid = validatePhoneUseCase(unformatted)
        view.phoneValidation(valid)
    }

    override fun validateForm(name: String, email: String, phone: String, register: Boolean) {
        val form = Form(
            name,
            email,
            phone.filter { it.isDigit() },
            register
        )
        val valid = validateFormUseCase(form)
        view.formValidation(valid)
    }
}