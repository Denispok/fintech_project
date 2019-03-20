package com.fintech.denispok.fintechproject.contacts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.customviews.InitialsRoundView
import kotlin.random.Random

class ContactsAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    companion object {
        const val LINEAR_VIEW_TYPE = 0
        const val GRID_VIEW_TYPE = 1
    }

    var viewType = LINEAR_VIEW_TYPE

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = when (viewType) {
            LINEAR_VIEW_TYPE -> LayoutInflater.from(viewGroup.context).inflate(
                R.layout.contacts_item_linear,
                viewGroup,
                false
            )
            GRID_VIEW_TYPE -> LayoutInflater.from(viewGroup.context).inflate(
                R.layout.contacts_item_grid,
                viewGroup,
                false
            )
            else -> throw IllegalStateException()
        }
        return ContactsViewHolder(view, viewType)
    }

    override fun onBindViewHolder(viewHolder: ContactsViewHolder, position: Int) {
        viewHolder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size

    override fun getItemViewType(position: Int) = viewType

    class ContactsViewHolder(itemView: View, val viewType: Int) : RecyclerView.ViewHolder(itemView) {
        private var roundView: InitialsRoundView = itemView.findViewById(R.id.contacts_item_round_view)
        private var name: TextView = itemView.findViewById(R.id.contacts_item_name)
        private var points: TextView? = itemView.findViewById(R.id.contacts_item_points)

        fun bind(contact: Contact) {
            roundView.initials = contact.parseInitials()
            roundView.roundColor = contact.color
            name.text = contact.name
            if (viewType == LINEAR_VIEW_TYPE) {
                points?.text = contact.getPointsString()
            }
        }
    }
}