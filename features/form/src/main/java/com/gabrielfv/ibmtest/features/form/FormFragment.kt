package com.gabrielfv.ibmtest.features.form

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabrielfv.ibmtest.features.form.text.MaskWatcher
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_form.*
import javax.inject.Inject

class FormFragment : Fragment(), FormContract.View {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextFields()
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

    private fun initTextFields() {
        setOf(wrapperNameInput, wrapperEmailInput, wrapperPhoneInput).forEach { inputLayout ->
            inputLayout.setEndIconOnClickListener {
                inputLayout.editText?.text?.clear()
            }
        }

        nameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) wrapperNameInput.setSuccess()
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
