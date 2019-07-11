package com.gabrielfv.ibmtest.libraries.design

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.navigation_bottom.view.*

class BottomNavigationBar
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val view: View = View.inflate(context, R.layout.navigation_bottom, this)

    var onItemSelectedListener: OnItemSelectedListener? = null

    var firstItem: String = ""
        set(value) {
            field = value
            view.navigationText1.text = value
        }

    var secondItem: String = ""
        set(value) {
            field = value
            view.navigationText2.text = value
        }

    var position: Position = Position.FIRST
        set(value) {
            updateViewPosition(value)
            field = value
        }

    companion object {
        private val HIGHLIGHT_TOP = 2.toDp()
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BottomNavigationBar,
            defStyleAttr, 0
        ).apply {
            try {
                firstItem = getString(R.styleable.BottomNavigationBar_firstItem) ?: ""
                secondItem = getString(R.styleable.BottomNavigationBar_secondItem) ?: ""

                position = getInt(R.styleable.BottomNavigationBar_position, 0).let {
                    Position.fromOrdinal(it)
                }
            } finally {
                recycle()
            }

            navigation1.setOnClickListener {
                position = Position.FIRST
            }

            navigation2.setOnClickListener {
                position = Position.SECOND
            }
        }
    }

    fun setOnItemSelectedListener(function: (Position) -> Unit) {
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun itemSelected(position: Position) {
                function(position)
            }
        }
    }

    fun updatePosition(position: Int) {
        this.position = Position.fromOrdinal(position)
    }

    private fun updateViewPosition(position: Position) {
        if (position == this.position) return
        ConstraintSet().apply {
            clone(view.bottomNavWrapper)
            switchTopMargin(position)
            moveHighlight(position)
            applyTo(view.bottomNavWrapper)
        }

        onItemSelectedListener?.itemSelected(position)
    }

    private fun ConstraintSet.switchTopMargin(position: Position) {
        val (selected, other) = when (position) {
            Position.FIRST -> Pair(R.id.navigation1, R.id.navigation2)
            Position.SECOND -> Pair(R.id.navigation2, R.id.navigation1)
        }

        connect(selected, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        connect(other, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, HIGHLIGHT_TOP)
    }

    private fun ConstraintSet.moveHighlight(position: Position) {
        val highlighted = when (position) {
            Position.FIRST -> R.id.navigation1
            Position.SECOND -> R.id.navigation2
        }

        connect(R.id.navigationHighlight, ConstraintSet.START, highlighted, ConstraintSet.START)
        connect(R.id.navigationHighlight, ConstraintSet.END, highlighted, ConstraintSet.END)
    }

    interface OnItemSelectedListener {
        fun itemSelected(position: Position)
    }

    enum class Position {
        FIRST,
        SECOND;

        companion object {
            fun fromOrdinal(ordinal: Int) = when (ordinal) {
                FIRST.ordinal -> FIRST
                SECOND.ordinal -> SECOND
                else -> FIRST
            }
        }
    }
}