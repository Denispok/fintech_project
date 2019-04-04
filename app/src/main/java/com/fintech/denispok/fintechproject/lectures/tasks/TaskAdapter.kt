package com.fintech.denispok.fintechproject.lectures.tasks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_task, viewGroup, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.task_item_title)
        private val deadline = itemView.findViewById<TextView>(R.id.task_item_deadline)
        private val status = itemView.findViewById<TextView>(R.id.task_item_status)
        private val mark = itemView.findViewById<TextView>(R.id.task_item_mark)

        fun bind(task: Task) {
            title.text = task.taskDescription.title

            task.taskDescription.deadlineDate?.apply {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val date = format.parse(this)
                deadline.text = date.toString()
            }

            status.text = task.status
            mark.text = "${task.mark}/${task.taskDescription.maxScore}"
        }
    }
}
