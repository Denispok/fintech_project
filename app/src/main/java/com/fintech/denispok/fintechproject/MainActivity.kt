package com.fintech.denispok.fintechproject

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    interface IOnBackPressed {
        fun onBackPressed()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, EventsFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_courses -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, CoursesFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, ProfileFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, EventsFragment()).commitNow()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
        if (fragment is IOnBackPressed) {
            fragment.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}
