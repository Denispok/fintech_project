package com.fintech.denispok.fintechproject

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }

    companion object {
        const val CURRENT_TAB_KEY = "current_tab"
        const val EVENTS_TAB = 0
        const val COURSES_TAB = 1
        const val PROFILE_TAB = 2
    }

    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: MainFragmentAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        bottomNavigationView = findViewById(R.id.navigation)

        viewPagerAdapter = MainFragmentAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 2

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            currentTab = when (item.itemId) {
                R.id.navigation_events -> EVENTS_TAB
                R.id.navigation_courses -> COURSES_TAB
                R.id.navigation_profile -> PROFILE_TAB
                else -> return@setOnNavigationItemSelectedListener false
            }
            viewPager.setCurrentItem(currentTab, false)
            updateTitle()
            true
        }

        currentTab = savedInstanceState?.getInt(CURRENT_TAB_KEY, 0) ?: 0
        updateTitle()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(viewPagerAdapter.getTag(currentTab))
        if (fragment is IOnBackPressed) {
            if (fragment.onBackPressed()) return
        }
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(CURRENT_TAB_KEY, currentTab)
        super.onSaveInstanceState(outState)
    }

    private fun updateTitle() {
        when (currentTab) {
            EVENTS_TAB -> supportActionBar?.title = getString(R.string.title_events)
            COURSES_TAB -> supportActionBar?.title = getString(R.string.title_courses)
            PROFILE_TAB -> supportActionBar?.title = getString(R.string.title_profile)
        }
    }
}
