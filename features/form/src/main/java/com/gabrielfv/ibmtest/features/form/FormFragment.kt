package com.gabrielfv.ibmtest.features.form

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_form.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FormFragment : Fragment(), FormContract.View {
    @Inject
    lateinit var presenter: FormContract.Presenter

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

        emailInput.setOnTouchListener { _, _ ->
            presenter.validateEmail("")
            true
        }

        startClearButtons()
    }

    override fun emailValidation(valid: Boolean) {
        if (valid) {
            wrapperEmailInput.setSuccess()
        } else {
            wrapperEmailInput.setError()
        }
    }

    override fun initPresenter(presenter: FormContract.Presenter) {
        this.presenter = presenter
    }

    private fun startClearButtons() {
        setOf(wrapperNameInput, wrapperEmailInput, wrapperPhoneInput).forEach { inputLayout ->
            inputLayout.setEndIconOnClickListener {
                inputLayout.editText?.text?.clear()
            }
        }
    }
}
