package com.gabrielfv.ibmtest.features.form

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
    lateinit var cellsAdapter: FormCellsAdapter

    private val validator = object : FormCellsAdapter.Validator {
        override fun validatePhone(phoneNumber: String) {
            presenter.validatePhone(phoneNumber)
        }

        override fun validateEmail(email: String) {
            presenter.validateEmail(email)
        }
    }

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
    }

    override fun informCellsLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun inflateCells(cells: List<Cell>) {
        loadingIndicator.visibility = View.GONE
        cellsAdapter = FormCellsAdapter(cells, validator) { form ->
            presenter.validateForm(form)
        }
        formCells.layoutManager = LinearLayoutManager(requireContext())
        formCells.adapter = cellsAdapter
    }

    override fun informCellsError(error: Throwable) {
        Log.e("ERROR", "E", error)
    }

    override fun emailValidation(valid: Boolean) {
        cellsAdapter.emailValidation(valid)
    }

    override fun phoneValidation(valid: Boolean) {
        cellsAdapter.phoneValidation(valid)
    }

    override fun formValidation(valid: Boolean) {
        if (valid) {
            stackController.pushFragment { SuccessFragment(stackController) }
        } else {
            // Undefined Behavior
        }
    }
}
