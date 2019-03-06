package com.fintech.denispok.fintechproject

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    interface IOnBackPressed {
        fun onBackPressed()
    }

    companion object {
        const val EVENTS_TAG = "events"
        const val COURSES_TAG = "courses"
        const val PROFILE_TAG = "profile"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                if (supportFragmentManager.findFragmentByTag(EVENTS_TAG) != null) {
                    supportFragmentManager.popBackStackImmediate(EVENTS_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } else {
                    supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, EventsFragment(), EVENTS_TAG).commitNow()
                }
                supportActionBar?.title = getString(R.string.title_events)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_courses -> {
                if (supportFragmentManager.findFragmentByTag(COURSES_TAG) != null) {
                    supportFragmentManager.popBackStackImmediate(COURSES_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } else {
                    supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, CoursesFragment(), COURSES_TAG).commitNow()
                }
                supportActionBar?.title = getString(R.string.title_courses)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                if (supportFragmentManager.findFragmentByTag(PROFILE_TAG) != null) {
                    supportFragmentManager.popBackStackImmediate(PROFILE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                } else {
                    supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_holder, ProfileFragment(), PROFILE_TAG).commitNow()
                }
                supportActionBar?.title = getString(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, EventsFragment(), EVENTS_TAG)
            .commitNow()
        supportActionBar?.title = getString(R.string.title_events)
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
