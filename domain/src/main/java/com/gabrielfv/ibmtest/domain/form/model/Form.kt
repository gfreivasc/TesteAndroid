package com.gabrielfv.ibmtest.domain.form.model

data class Form(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val registerEmail: Boolean
)