package com.gabrielfv.ibmtest.features.form

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.gabrielfv.ibmtest.domain.form.model.*
import com.gabrielfv.ibmtest.features.form.text.PhoneWatcher
import com.gabrielfv.ibmtest.libraries.design.toDp
import com.gabrielfv.ibmtest.libraries.design.widgets.TextInputLayout
import kotlinx.android.synthetic.main.list_item_selector_cell.view.*
import kotlinx.android.synthetic.main.list_item_submit_cell.view.*
import kotlinx.android.synthetic.main.list_item_text_input_cell.view.*
import kotlinx.android.synthetic.main.list_item_text_label_cell.view.*

class FormCellsAdapter(
    cells: List<Cell>,
    private val validator: Validator,
    private val submit: (Form) -> Unit
) : RecyclerView.Adapter<FormCellsAdapter.CellViewHolder>() {
    private var emailValidationListener: Validator.Listener? = null
    private var phoneValidationListener: Validator.Listener? = null

    private val phoneNumberWatcher = PhoneWatcher()

    private val cellFields = cells.map { cell ->
        val field = if (cell.hidden) {
            null
        } else {
            when (cell.type) {
                Cell.InputType.FIELD -> TextField("", cell.typefield ?: Cell.DataType.TEXT)
                Cell.InputType.CHECKBOX -> SelectorField(false)
                else -> null
            }
        }
        CellField(cell, field)
    }

    companion object {
        const val PHONE_MAX_LENGTH = 15
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            Cell.InputType.FIELD.serialized -> {
                val itemView = inflater
                    .inflate(R.layout.list_item_text_input_cell, parent, false)

                return TextInputCellViewHolder(itemView)
            }
            Cell.InputType.TEXT.serialized -> {
                val itemView = inflater
                    .inflate(R.layout.list_item_text_label_cell, parent, false)

                return TextLabelCellViewHolder(itemView)
            }
            Cell.InputType.SEND.serialized -> {
                val itemView = inflater
                    .inflate(R.layout.list_item_submit_cell, parent, false)

                return SubmitCellViewHolder(itemView)
            }
            Cell.InputType.CHECKBOX.serialized -> {
                val itemView = inflater
                    .inflate(R.layout.list_item_selector_cell, parent, false)

                return SelectorCellViewHolder(itemView)
            }
            else -> {
                val itemView = inflater
                    .inflate(R.layout.list_item_text_label_cell, parent, false)

                return TextLabelCellViewHolder(itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = cellFields[position].cell.type.serialized

    override fun getItemCount(): Int = cellFields.size

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(cellFields[position])
    }

    override fun onViewRecycled(holder: CellViewHolder) {
        super.onViewRecycled(holder)
        // Prevent leaking listeners
        if (holder is TextInputCellViewHolder) {
            when (holder.type) {
                Cell.DataType.EMAIL -> emailValidationListener = null
                Cell.DataType.PHONE -> phoneValidationListener = null
                else -> { }
            }
        }
    }

    fun emailValidation(valid: Boolean) {
        emailValidationListener?.validation(valid)
    }

    fun phoneValidation(valid: Boolean) {
        phoneValidationListener?.validation(valid)
    }

    data class CellField(
        var cell: Cell,
        var input: FormField?
    )

    abstract class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cellField: CellField) {
            if (cellField.cell.hidden) {
                itemView.visibility = View.GONE
                itemView.layoutParams = ConstraintLayout.LayoutParams(0, 0)
            } else {
                itemView.visibility = View.VISIBLE
                itemView.layoutParams = ConstraintLayout.LayoutParams(
                    MATCH_PARENT,
                    WRAP_CONTENT
                )
                onBind(cellField)
            }
        }

        protected abstract fun onBind(cellField: CellField)

        protected fun View.applyTopMargin(cell: Cell) {
            val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = cell.topSpacing.toInt().toDp()
            this.layoutParams = layoutParams
        }
    }

    inner class TextInputCellViewHolder(itemView: View) : CellViewHolder(itemView) {
        var type: Cell.DataType? = null

        override fun onBind(cellField: CellField) {
            itemView.apply {
                wrapperTextInput.hint = cellField.cell.message
                wrapperTextInput.editText?.let { editText ->
                    editText.removeTextChangedListener(phoneNumberWatcher)
                    applyEditTextRules(wrapperTextInput, editText, cellField.cell.typefield)
                    supplyInputIfExists(editText, cellField)
                }
                wrapperTextInput.applyTopMargin(cellField.cell)
                considerListenValidations(wrapperTextInput, cellField.cell.typefield)
                wrapperTextInput.setEndIconOnClickListener {
                    wrapperTextInput.editText?.text?.clear()
                    wrapperTextInput.setError()
                }
            }

            type = cellField.cell.typefield
        }

        private fun applyEditTextRules(wrapper: TextInputLayout, editText: EditText, type: Cell.DataType?) {
            editText.inputType = when (type) {
                Cell.DataType.TEXT -> InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                Cell.DataType.EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                Cell.DataType.PHONE -> InputType.TYPE_CLASS_PHONE
                else -> InputType.TYPE_CLASS_TEXT
            }

            editText.addTextChangedListener(FieldWatcher(adapterPosition, type ?: Cell.DataType.TEXT))

            if (type == Cell.DataType.EMAIL) {
                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) validator.validateEmail(editText.text.toString())
                }
            } else if (type == Cell.DataType.PHONE) {
                editText.addTextChangedListener(PhoneWatcher())
                editText.filters = arrayOf(InputFilter.LengthFilter(PHONE_MAX_LENGTH))
                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) validator.validatePhone(editText.text.toString())
                }
            } else {
                editText.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        if (editText.text.isNotEmpty()) wrapper.setSuccess()
                        else wrapper.setError()
                    }
                }
            }
        }

        private fun considerListenValidations(wrapper: TextInputLayout, type: Cell.DataType?) {
            if (type == Cell.DataType.EMAIL) {
                emailValidationListener = createListener(wrapper)
            } else if (type == Cell.DataType.PHONE) {
                phoneValidationListener = createListener(wrapper)
            }
        }

        private fun supplyInputIfExists(editText: EditText, cellField: CellField) {
            val field = cellField.input as? TextField
            field?.text
                ?.takeIf { it.isNotEmpty() }
                ?.let { editText.setText(it) }
        }

        private fun createListener(wrapper: TextInputLayout) = object : Validator.Listener {
            override fun validation(valid: Boolean) {
                if (valid) {
                    wrapper.setSuccess()
                } else {
                    wrapper.setError()
                }
            }
        }

        private inner class FieldWatcher(
            private val adapterPosition: Int,
            private val type: Cell.DataType
        ) : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                cellFields[adapterPosition].input = TextField(
                    text.toString(),
                    type
                )
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        }
    }

    class TextLabelCellViewHolder(itemView: View) : CellViewHolder(itemView) {

        override fun onBind(cellField: CellField) {
            itemView.apply {
                label.text = cellField.cell.message
                label.applyTopMargin(cellField.cell)
            }
        }
    }

    inner class SubmitCellViewHolder(itemView: View) : CellViewHolder(itemView) {

        override fun onBind(cellField: CellField) {
            itemView.submitButton.apply {
                text = cellField.cell.message
                applyTopMargin(cellField.cell)
                setOnClickListener {
                    submit(Form(cellFields.mapNotNull { it.input }))
                }
            }
        }
    }

    inner class SelectorCellViewHolder(itemView: View) : CellViewHolder(itemView) {

        override fun onBind(cellField: CellField) {
            itemView.selector.apply {
                text = cellField.cell.message
                isChecked = (cellField.input as? SelectorField)?.selected ?: false
                setOnClickListener {
                    cellField.input = SelectorField(isChecked)
                    considerShow(isChecked, cellField.cell.show)
                }
            }
        }

        private fun considerShow(active: Boolean, item: Int?) {
            if (item == null) return
            cellFields.find { it.cell.id == item }
                ?.let { cellField ->
                    if (active != !cellField.cell.hidden) {
                        cellField.cell = cellField.cell.copy(hidden = !active)
                        notifyDataSetChanged()
                    }
                }
        }
    }

    interface Validator {
        fun validatePhone(phoneNumber: String)
        fun validateEmail(email: String)

        interface Listener {
            fun validation(valid: Boolean)
        }
    }
}