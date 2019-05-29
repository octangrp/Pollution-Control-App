package com.rema.pollutioncontrol.controllers.ui.main

import android.content.Context

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rema.pollutioncontrol.R
import com.rema.pollutioncontrol.models.Location

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, var locations: ArrayList<Location>)
    : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, locations[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }

    override fun getCount(): Int {
        return locations.size
    }
}