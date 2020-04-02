package com.example.habit.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.habit.Habit
import com.example.habit.fragments.HabitsListFragment

class HabitsListViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            HabitsListFragment.newInstance(1)
        } else {
            HabitsListFragment.newInstance(0)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0)
            "good"
        else
            "bad"
    }

    override fun getCount(): Int {
        return 2
    }
}
