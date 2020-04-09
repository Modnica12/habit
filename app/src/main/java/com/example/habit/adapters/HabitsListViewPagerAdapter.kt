package com.example.habit.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.habit.Habit
import com.example.habit.fragments.HabitsListFragment
import com.example.habit.fragments.HabitsListViewModel
import com.example.habit.fragments.LOG_DEBUG

class HabitsListViewPagerAdapter(fm: FragmentManager, private val viewModel: HabitsListViewModel) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        Log.d(LOG_DEBUG, "LISTS")
        return if (position == 0) {
            HabitsListFragment.newInstance(1, viewModel)
        } else {
            HabitsListFragment.newInstance(0, viewModel)
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
