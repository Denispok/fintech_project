package com.fintech.denispok.fintechproject.lectures

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Lecture

class LecturesAdapter(var lectures: List<Lecture>, private val onLectureClickListener: OnLectureClickListener) : RecyclerView.Adapter<LecturesAdapter.LectureViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LectureViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_lecture, viewGroup, false)
        view.setOnClickListener {
            val lecture = view.tag as Lecture
            onLectureClickListener.onLectureClick(lecture)
        }
        return LectureViewHolder(view)
    }

    override fun getItemCount(): Int = lectures.size

    override fun onBindViewHolder(viewHolder: LectureViewHolder, position: Int) {
        viewHolder.bind(lectures[position])
    }

    class LectureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView.findViewById<TextView>(R.id.lecture_item_title)

        fun bind(lecture: Lecture) {
            itemView.tag = lecture
            titleView.text = lecture.title
        }
    }

    interface OnLectureClickListener {
        fun onLectureClick(lecture: Lecture)
    }
}
