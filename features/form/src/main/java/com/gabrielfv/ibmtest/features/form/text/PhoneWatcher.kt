package com.gabrielfv.ibmtest.features.form.text

import android.text.Editable
import android.text.TextWatcher

class PhoneWatcher : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    companion object {
        const val PHONE_MASK = "(##) ####-####"
        const val PHONE_MASK_9 = "(##) #####-####"
    }

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true

        val editableLength = editable.length

        if (editableLength < PHONE_MASK_9.length) {
            if (PHONE_MASK_9[editableLength] != '#') {
                editable.append(PHONE_MASK_9[editableLength])
            } else if (PHONE_MASK_9[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, PHONE_MASK_9, editableLength - 1, editableLength)
            }
        }

        isRunning = false
    }
}