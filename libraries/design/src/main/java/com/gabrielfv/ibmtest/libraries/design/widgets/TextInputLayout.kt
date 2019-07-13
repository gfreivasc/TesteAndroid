package com.gabrielfv.ibmtest.libraries.design.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.gabrielfv.ibmtest.libraries.design.R
import com.google.android.material.textfield.TextInputLayout

class TextInputLayout
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): TextInputLayout(context, attrs, defStyleAttr) {

    override fun setError(errorText: CharSequence?) {
        val drawable = ResourcesCompat
            .getDrawable(context.resources,
                R.drawable.input_text_error_background, context.theme)

        drawable?.let { updateEditTextBackground(it) }
    }

    fun setError() {
        error = ""
    }

    fun setSuccess() {
        val drawable = ResourcesCompat
            .getDrawable(context.resources,
                R.drawable.input_text_valid_background, context.theme)

        drawable?.let { updateEditTextBackground(it) }
    }

    private fun updateEditTextBackground(background: Drawable) {
        editText?.let { it.background = background }
    }
}