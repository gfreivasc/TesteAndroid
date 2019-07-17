package com.gabrielfv.ibmtest.libraries.design.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.gabrielfv.ibmtest.libraries.design.R

class Button
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): Button(context, attrs) {

    init {
        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                ACTION_DOWN -> inAnimation()
                ACTION_UP -> outAnimation()
            }
            true
        }
    }

    private fun outAnimation() {
        val outAnim = AnimationUtils.loadAnimation(context,
            R.anim.opacity_bounce_out
        )
        outAnim.fillAfter = true

        startAnimation(outAnim)
    }

    private fun inAnimation() {
        val inAnim = AnimationUtils.loadAnimation(context,
            R.anim.opacity_bounce_in
        )
        inAnim.fillAfter = true

        startAnimation(inAnim)
    }
}