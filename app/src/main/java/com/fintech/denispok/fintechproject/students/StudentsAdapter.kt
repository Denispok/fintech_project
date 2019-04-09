package com.fintech.denispok.fintechproject.students

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.customviews.InitialsRoundView

class StudentsAdapter(var students: List<Student>) :
        RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    companion object {
        const val LINEAR_VIEW_TYPE = 0
        const val GRID_VIEW_TYPE = 1
    }

    var viewType = LINEAR_VIEW_TYPE

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StudentViewHolder {
        val view = when (viewType) {
            LINEAR_VIEW_TYPE -> LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.student_item_linear,
                    viewGroup,
                    false
            )
            GRID_VIEW_TYPE -> LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.student_item_grid,
                    viewGroup,
                    false
            )
            else -> throw IllegalStateException()
        }
        return StudentViewHolder(view, viewType)
    }

    override fun onBindViewHolder(viewHolder: StudentViewHolder, position: Int) {
        viewHolder.bind(students[position])
    }

    override fun getItemCount() = students.size

    override fun getItemViewType(position: Int) = viewType

    class StudentViewHolder(itemView: View, val viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private var roundView: InitialsRoundView = itemView.findViewById(R.id.contacts_item_round_view)
        private var name: TextView = itemView.findViewById(R.id.contacts_item_name)
        private var points: TextView? = itemView.findViewById(R.id.contacts_item_points)

        fun bind(student: Student) {
            roundView.parseInitials(student.name)
            roundView.roundColor = student.color
            name.text = student.name
            if (viewType == LINEAR_VIEW_TYPE) {
                points?.text = student.mark.toString()
            }
        }
    }
}
