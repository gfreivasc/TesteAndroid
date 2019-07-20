package com.gabrielfv.ibmtest.libraries.design.widgets

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class TextInputEditText
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArgs: Int = com.google.android.material.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleArgs) {
    private val registeredWatchers = mutableListOf<TextWatcher>()

    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
        watcher?.let { registeredWatchers.add(it) }
    }

    fun addSingleTextChangedListener(watcher: TextWatcher) {
        val hasSameClass = registeredWatchers.any { it::class == watcher::class }
        if (hasSameClass) return
        else addTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        registeredWatchers.removeAll { watcher ->
            removeTextChangedListener(watcher)
            true
        }
    }
}