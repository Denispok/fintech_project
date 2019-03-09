package com.fintech.denispok.fintechproject.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.fintech.denispok.fintechproject.R

class BadgeView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    val src: Drawable?
    val name: String
    val count: Int

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BadgeView,
            0, 0
        ).apply {
            try {
                src = getDrawable(R.styleable.BadgeView_src)
                name = getString(R.styleable.BadgeView_name) ?: ""
                count = getInt(R.styleable.BadgeView_count, 0)
            } finally {
                recycle()
            }
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_badge, this, true)
        view.findViewById<TextView>(R.id.badge_name).text = name
        view.findViewById<ImageView>(R.id.badge_card_image).setImageDrawable(src)
        view.findViewById<TextView>(R.id.badge_count).text = count.toString()
    }
}
