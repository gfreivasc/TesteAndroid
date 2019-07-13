package com.gabrielfv.ibmtest.features.form

class FormPresenter(
    private val view: FormContract.View
) : FormContract.Presenter {

    override fun start() {

    }

    override fun validateEmail(email: String) {
        view.emailValidation(true)
    }
}