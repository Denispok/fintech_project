package com.fintech.denispok.fintechproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.fintech.denispok.fintechproject.MainActivity.Companion.COURSES_TAB
import com.fintech.denispok.fintechproject.MainActivity.Companion.EVENTS_TAB
import com.fintech.denispok.fintechproject.MainActivity.Companion.PROFILE_TAB
import com.fintech.denispok.fintechproject.courses.CoursesFragment
import com.fintech.denispok.fintechproject.profile.ProfileWrapperFragment

class MainFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val TAB_MAIN_COUNT = 3

    private var eventsFragmentTag: String? = null
    private var coursesFragmentTag: String? = null
    private var profileFragmentTag: String? = null

    override fun getItem(position: Int): Fragment {
        return when (position) {
            EVENTS_TAB -> EventsFragment()
            COURSES_TAB -> CoursesFragment()
            PROFILE_TAB -> ProfileWrapperFragment()
            else -> throw IllegalArgumentException("Position = $position, but it should be less then $TAB_MAIN_COUNT")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val createdFragment = super.instantiateItem(container, position) as Fragment
        when (position) {
            EVENTS_TAB -> eventsFragmentTag = createdFragment.tag
            COURSES_TAB -> coursesFragmentTag = createdFragment.tag
            PROFILE_TAB -> profileFragmentTag = createdFragment.tag
        }
        return createdFragment
    }

    override fun getCount() = TAB_MAIN_COUNT

    fun getTag(position: Int) = when (position) {
        EVENTS_TAB -> eventsFragmentTag
        COURSES_TAB -> coursesFragmentTag
        PROFILE_TAB -> profileFragmentTag
        else -> null
    }
}