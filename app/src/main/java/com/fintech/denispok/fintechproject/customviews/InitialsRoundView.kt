package com.fintech.denispok.fintechproject.customviews

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.fintech.denispok.fintechproject.R

class InitialsRoundView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    val drawable = context.getDrawable(R.drawable.view_round_shape)!!
    var initials: String = ""
        set(value) {
            field = value
            text = value
        }
    var roundColor: Int = 0
        set(value) {
            field = value
            drawable.setColorFilter(value, PorterDuff.Mode.SRC)
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InitialsRoundView,
            0, 0
        ).apply {
            try {
                initials = getString(R.styleable.InitialsRoundView_initials) ?: ""
                roundColor = getColor(R.styleable.InitialsRoundView_roundColor, 0)
            } finally {
                recycle()
            }
        }
        background = drawable
        gravity = Gravity.CENTER
    }

    fun parseInitials(string: String) {
        val names = string.split(" ")
        var initials = ""
        for (name in names) {
            initials += name[0].toUpperCase()
            if (initials.length >= 2) break
        }
        this.initials = initials
    }
}