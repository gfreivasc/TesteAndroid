package com.gabrielfv.ibmtest.features.form

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabrielfv.ibmtest.domain.form.model.Cell
import com.gabrielfv.ibmtest.features.form.success.SuccessFragment
import com.gabrielfv.ibmtest.features.form.text.MaskWatcher
import com.gabrielfv.ibmtest.libraries.core.ViewPagerStackController
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_form.*
import javax.inject.Inject

class FormFragment(
    private val stackController: ViewPagerStackController
) : Fragment(), FormContract.View {
    @Inject
    lateinit var presenter: FormContract.Presenter

    private val phoneWatcher = MaskWatcher(PHONE_MASK)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_form, container, false)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.dispose()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        initTextFields()
        submit.setOnClickListener {
            presenter.validateForm(
                nameInput.text.toString(),
                emailInput.text.toString(),
                phoneInput.text.toString(),
                true
            )
        }
    }

    override fun inflateCells(cells: List<Cell>) {
        // TODO: Inflate cells on RecyclerView
    }

    override fun informCellsError() {
        // Undefined Behavior
    }

    override fun emailValidation(valid: Boolean) {
        if (valid) {
            wrapperEmailInput.setSuccess()
        } else {
            wrapperEmailInput.setError()
        }
    }

    override fun phoneValidation(valid: Boolean) {
        if (valid) {
            wrapperPhoneInput.setSuccess()
        } else {
            wrapperPhoneInput.setError()
        }
    }

    override fun formValidation(valid: Boolean) {
        if (valid) {
            stackController.pushFragment { SuccessFragment(stackController) }
        } else {

        }
    }

    private fun initTextFields() {
        setOf(wrapperNameInput, wrapperEmailInput, wrapperPhoneInput).forEach { inputLayout ->
            inputLayout.setEndIconOnClickListener {
                inputLayout.editText?.text?.clear()
            }
        }

        nameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && nameInput.text?.isNotEmpty() == true) wrapperNameInput.setSuccess()
        }

        emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) presenter.validateEmail(emailInput.text.toString())
        }

        phoneInput.addTextChangedListener(phoneWatcher)
        phoneInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) presenter.validatePhone(phoneInput.text.toString())
        }
    }

    companion object {
        const val PHONE_MASK = "(##) #####-####"
    }
}
