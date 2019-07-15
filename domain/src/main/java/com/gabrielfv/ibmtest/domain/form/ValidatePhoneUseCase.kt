package com.gabrielfv.ibmtest.domain.form

import javax.inject.Inject

class ValidatePhoneUseCase @Inject constructor() {

    operator fun invoke(number: String): Boolean {
        if (number.length != 11) return false
        if (validDDDRegex.containsMatchIn(number.take(2))) return true
        return false
    }

    companion object {
        private val validDDDRegex = Regex("""
            ([1,4,6,8,9][1-9])|(2[1,2,4,7,8])|(3[1-5,7,8])|(5[1,3-5])|(7[1,3-5,7,9])
        """.trimIndent().replace("\n", ""))
    }
}