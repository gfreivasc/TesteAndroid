package com.gabrielfv.ibmtest.features.form.text

import android.text.Editable
import android.text.TextWatcher

class PhoneWatcher : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    private var mask: String = PHONE_MASK

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

        mask = if (editableLength == PHONE_MASK.length) {
            PHONE_MASK_9
        } else {
            PHONE_MASK
        }

        if (editableLength < mask.length) {
            if (mask[editableLength] != '#') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }

        isRunning = false
    }
}