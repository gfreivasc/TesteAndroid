package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.libraries.core.CorePresenter

interface FormContract {

    interface View {
        fun emailValidation(valid: Boolean)
        fun phoneValidation(valid: Boolean)
    }

    interface Presenter : CorePresenter {
        fun validateEmail(email: String)
        fun validatePhone(phone: String)
    }
}