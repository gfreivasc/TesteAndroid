package com.gabrielfv.ibmtest.domain.form

import com.gabrielfv.ibmtest.domain.form.model.Form
import javax.inject.Inject

class ValidateFormUseCase @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val validatePhone: ValidatePhoneUseCase
) {

    operator fun invoke(form: Form): Boolean {
        return form.name.isNotEmpty()
                && validateEmail(form.email)
                && validatePhone(form.phoneNumber)
    }
}