package com.gabrielfv.ibmtest.domain.form.model

data class Form(
    val fields: List<FormField>
)

sealed class FormField
data class TextField(val text: String, val type: Cell.DataType) : FormField()
data class SelectorField(val selected: Boolean) : FormField()