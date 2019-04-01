package com.fintech.denispok.fintechproject.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.fintech.denispok.fintechproject.R

class BadgeView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    val src: Drawable?
    val name: String
    var count: Int = 0
        set(value) {
            field = value
            badgeCountView.text = field.toString()
        }

    private val badgeCountView: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_badge, this, true)
        badgeCountView = view.findViewById(R.id.badge_count)

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

        view.findViewById<ImageView>(R.id.badge_card_image).setImageDrawable(src)
        view.findViewById<TextView>(R.id.badge_name).text = name
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() ?: return null
        return SavedState(superState, count)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        if (state is SavedState) {
            count = state.count
        }
    }

    companion object {
        class SavedState(superState: Parcelable, val count: Int) : BaseSavedState(superState) {

            override fun writeToParcel(out: Parcel, flags: Int) {
                super.writeToParcel(out, flags)
                out.writeInt(count)
            }
        }
    }
}
