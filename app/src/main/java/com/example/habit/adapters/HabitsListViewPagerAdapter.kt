package com.example.habit.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HabitsListViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    private val fragmentsList = ArrayList<Fragment>()
    private val titlesList = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String){
        fragmentsList.add(fragment)
        titlesList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlesList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }
}
