package com.gabrielfv.ibmtest.domain.form.model

import com.google.gson.annotations.SerializedName

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
        @SerializedName("1") FIELD(1),
        @SerializedName("2") TEXT(2),
        @SerializedName("3") IMAGE(3),
        @SerializedName("4") CHECKBOX(4),
        @SerializedName("5") SEND(5)
    }

    enum class DataType(val serialized: Int, alt: String? = null) {
        @SerializedName("1") TEXT(1),
        @SerializedName("telnumber") PHONE(2, "telnumber"),
        @SerializedName("3") EMAIL(3)
    }
}