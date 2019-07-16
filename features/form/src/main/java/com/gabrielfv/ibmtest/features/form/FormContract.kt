package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.domain.form.model.Cell
import com.gabrielfv.ibmtest.libraries.core.CorePresenter

interface FormContract {

    interface View {
        fun inflateCells(cells: List<Cell>)
        fun informCellsError()
        fun emailValidation(valid: Boolean)
        fun phoneValidation(valid: Boolean)
        fun formValidation(valid: Boolean)
    }

    interface Presenter : CorePresenter {
        fun validateEmail(email: String)
        fun validatePhone(phone: String)
        fun validateForm(name: String, email: String, phone: String, register: Boolean)
    }
}