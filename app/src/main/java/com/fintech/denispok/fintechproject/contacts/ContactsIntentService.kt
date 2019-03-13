package com.fintech.denispok.fintechproject.contacts

import android.app.IntentService
import android.content.Intent
import android.provider.ContactsContract
import android.support.v4.content.LocalBroadcastManager

class ContactsIntentService : IntentService("contacts_service") {

    companion object {
        const val INTENT_CONTACTS = "contacts"
        const val KEY_CONTACTS = "contacts"
    }

    override fun onHandleIntent(intent: Intent?) {
        val contacts = arrayListOf<Contact>()

        val cursor = applicationContext.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
            null, null, ContactsContract.Contacts.DISPLAY_NAME
        )!!

        if (cursor.count != 0) {
            cursor.moveToNext()
            for (i in 1..cursor.count) {
                contacts.add(Contact(cursor.getInt(0), cursor.getString(1)))
                cursor.moveToNext()
            }
        }

        cursor.close()

        val responseIntent = Intent(INTENT_CONTACTS).putExtra(KEY_CONTACTS, contacts)
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(responseIntent)
    }
}