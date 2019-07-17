package com.gabrielfv.ibmtest.domain.form

import com.gabrielfv.ibmtest.domain.form.model.*
import javax.inject.Inject

class ValidateFormUseCase @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val validatePhone: ValidatePhoneUseCase
) {

    operator fun invoke(form: Form): Boolean {
        return form.fields.all { field ->
            if (field is TextField) {
                when (field.type) {
                    Cell.DataType.TEXT -> field.text.isNotEmpty()
                    Cell.DataType.EMAIL -> validateEmail(field.text)
                    Cell.DataType.PHONE -> validatePhone(field.text)
                }
            } else true
        }
    }
}