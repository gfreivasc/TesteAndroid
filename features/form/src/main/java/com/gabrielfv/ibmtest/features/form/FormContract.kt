package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.libraries.core.CorePresenter

interface FormContract {

    interface View {
        fun emailValidation(valid: Boolean)
    }

    interface Presenter : CorePresenter {
        fun validateEmail(email: String)
    }
}