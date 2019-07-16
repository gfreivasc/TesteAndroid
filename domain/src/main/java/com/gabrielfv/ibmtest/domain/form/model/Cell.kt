package com.gabrielfv.ibmtest.domain.form.model

data class Cell(
    val id: Int,
    val type: InputType,
    val message: String,
    val typefield: DataType?,
    val hidden: Boolean,
    val topSpacing: Double,
    val show: Int?,
    val required: Boolean
) {
    enum class InputType(val serialized: Int) {
        FIELD(1),
        TEXT(2),
        IMAGE(3),
        CHECKBOX(4),
        SEND(5)
    }

    enum class DataType(val serialized: Int, alt: String? = null) {
        TEXT(1),
        PHONE(2, "telnumber"),
        EMAIL(3)
    }
}