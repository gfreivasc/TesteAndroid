package com.gabrielfv.ibmtest.features.form

import com.gabrielfv.ibmtest.domain.form.FetchCellsUseCase
import com.gabrielfv.ibmtest.domain.form.ValidateEmailUseCase
import com.gabrielfv.ibmtest.domain.form.ValidateFormUseCase
import com.gabrielfv.ibmtest.domain.form.ValidatePhoneUseCase
import com.gabrielfv.ibmtest.domain.form.model.Cell
import com.gabrielfv.ibmtest.domain.form.model.Form
import com.gabrielfv.ibmtest.domain.form.model.TextField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FormPresenter(
    private val view: FormContract.View,
    private val fetchCellsUseCase: FetchCellsUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val validateFormUseCase: ValidateFormUseCase
) : FormContract.Presenter {
    private val disposables = mutableListOf<Disposable>()

    override fun start() {
        fetchCellsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view.inflateCells(it) },
                { view.informCellsError() }
            )
            .let { disposables.add(it) }
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

    override fun validateForm(form: Form) {
        val cleanedForm = form.fields.map { field ->
            if (field is TextField && field.type == Cell.DataType.PHONE) {
                TextField(field.text.filter { it.isDigit() }, field.type)
            } else {
                field
            }
        }.let { cleanedFields -> Form(cleanedFields) }

        val valid = validateFormUseCase(cleanedForm)
        view.formValidation(valid)
    }

    override fun dispose() {
        disposables.forEach { it.dispose() }
    }
}