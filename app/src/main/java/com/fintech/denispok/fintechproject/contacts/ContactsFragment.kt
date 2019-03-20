package com.fintech.denispok.fintechproject.contacts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.contacts.ContactsAdapter.Companion.GRID_VIEW_TYPE
import com.fintech.denispok.fintechproject.contacts.ContactsAdapter.Companion.LINEAR_VIEW_TYPE
import com.fintech.denispok.fintechproject.contacts.ContactsIntentService.Companion.INTENT_CONTACTS
import com.fintech.denispok.fintechproject.contacts.ContactsIntentService.Companion.KEY_CONTACTS

class ContactsFragment : Fragment() {

    private lateinit var reciever: BroadcastReceiver
    private lateinit var recyclerView: RecyclerView
    private var recyclerAdapter: ContactsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        recyclerView = view.findViewById(R.id.contacts_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        reciever = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val contacts = intent?.getSerializableExtra(KEY_CONTACTS)
                recyclerAdapter = ContactsAdapter(contacts as List<Contact>)
                recyclerView.adapter = recyclerAdapter
            }
        }
        LocalBroadcastManager.getInstance(context!!).registerReceiver(reciever, IntentFilter(INTENT_CONTACTS))
        context!!.startService(Intent(context, ContactsIntentService::class.java))

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(reciever)
    }

    fun changeView() {
        when (recyclerAdapter?.viewType) {
            LINEAR_VIEW_TYPE -> {
                recyclerView.layoutManager = GridLayoutManager(context, 3)
                recyclerAdapter?.viewType = GRID_VIEW_TYPE
            }
            GRID_VIEW_TYPE -> {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerAdapter?.viewType = LINEAR_VIEW_TYPE
            }
        }
    }
}