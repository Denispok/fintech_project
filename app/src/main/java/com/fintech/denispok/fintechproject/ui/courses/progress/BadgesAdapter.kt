package com.fintech.denispok.fintechproject.ui.courses.progress

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.customviews.BadgeView

class BadgesAdapter : RecyclerView.Adapter<BadgesAdapter.BadgeViewHolder>() {

    var students: List<Student> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = BadgeView(viewGroup.context, null)
        return BadgeViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(itemView: BadgeViewHolder, position: Int) {
        val student = students[position]
        itemView.badgeView.apply {
            name = student.name
            count = student.mark.toInt()
        }
    }

    class BadgeViewHolder(val badgeView: BadgeView) : RecyclerView.ViewHolder(badgeView) {
        init {
            badgeView.src = App.applicationContext.getDrawable(R.drawable.goodboy_image)
        }
    }
}
