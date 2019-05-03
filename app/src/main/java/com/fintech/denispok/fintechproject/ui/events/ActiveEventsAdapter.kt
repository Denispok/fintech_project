package com.fintech.denispok.fintechproject.ui.events

import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Event
import java.text.SimpleDateFormat
import java.util.*

class ActiveEventsAdapter : RecyclerView.Adapter<ActiveEventsAdapter.ActiveEventViewHolder>() {

    var events: List<Event> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    private val outputDateFormat = SimpleDateFormat("MMM y", Locale.ENGLISH)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActiveEventViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_event_active, viewGroup, false)
        return ActiveEventViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(itemView: ActiveEventViewHolder, position: Int) {
        val event = events[position]
        val resources = App.applicationContext.resources

        val normalMargin = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics).toInt()
        val startEndMargin = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()
        val marginLayoutParams = itemView.itemView.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.marginStart = if (position == 0) startEndMargin else normalMargin
        marginLayoutParams.marginEnd = if (position == events.size - 1) startEndMargin else normalMargin

        itemView.apply {
            val eventType = event.eventType
            if (eventType != null) {
                var text = eventType.name
                if (text.length > 14) {
                    text = text.slice(0..10) + "..."
                }
                eventTypeView.text = text

                val color = when (eventType.color) {
                    "green" -> resources.getColor(R.color.colorGreen)
                    "purple" -> resources.getColor(R.color.colorPurple)
                    "orange" -> resources.getColor(R.color.colorOrange)
                    else -> resources.getColor(R.color.colorPurple)
                }
                eventTypeView.background.setColorFilter(color, PorterDuff.Mode.ADD)
                eventTypeView.visibility = View.VISIBLE
            } else {
                eventTypeView.visibility = View.GONE
            }

            if (event.customDate.isEmpty()) {
                val date = inputDateFormat.parse(event.dateStart)
                dateView.text = outputDateFormat.format(date)
            } else {
                dateView.text = event.customDate
            }

            titleView.text = event.title
        }
    }

    class ActiveEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.item_event_active_image)
        val eventTypeView = itemView.findViewById<TextView>(R.id.item_event_active_event_type)
        val dateView = itemView.findViewById<TextView>(R.id.item_event_active_date)
        val titleView = itemView.findViewById<TextView>(R.id.item_event_active_title)
    }
}
