package com.fintech.denispok.fintechproject.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fintech.denispok.fintechproject.R

class ContactsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_READ_CONTACTS = 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contacts_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.contacts_action_bar_change_view) {
            val fragment = supportFragmentManager.findFragmentById(R.id.activity_contacts)
            if (fragment is ContactsFragment) {
                fragment.changeView()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        if (Build.VERSION.SDK_INT >= 23) {
            if (applicationContext.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS)
            } else {
                startFragment()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startFragment()
            }
        }
    }

    private fun startFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_contacts, ContactsFragment())
            .commit()
    }
}
