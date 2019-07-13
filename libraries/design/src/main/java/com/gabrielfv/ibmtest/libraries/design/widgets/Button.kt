package com.gabrielfv.ibmtest.libraries.design.widgets

import android.content.Context
import android.util.AttributeSet
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
        setOnClickListener {
            initAnimation()
        }
    }

    private fun initAnimation() {
        val inAnim = AnimationUtils.loadAnimation(context,
            R.anim.opacity_bounce_in
        )
        val outAnim = AnimationUtils.loadAnimation(context,
            R.anim.opacity_bounce_out
        )

        inAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) { }
            override fun onAnimationRepeat(p0: Animation?) { }

            override fun onAnimationEnd(p0: Animation?) {
                startAnimation(outAnim)
            }
        })

        outAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) { }
            override fun onAnimationRepeat(p0: Animation?) { }

            override fun onAnimationEnd(p0: Animation?) {
                isClickable = true
            }
        })

        isClickable = false
        startAnimation(inAnim)
    }
}