package com.example.habit.adapters

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.data.LOG_DEBUG
import com.example.habit.presentation.fragments.BottomSheetFragment
import com.example.habit.presentation.fragments.HabitsListFragment
import java.lang.IllegalStateException

class HabitsListViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        Log.d(LOG_DEBUG, "LISTS")
        val bottomSheetFragment = BottomSheetFragment()
        return when (position) {
            0 -> HabitsListFragment.newInstance(0)
            1 -> HabitsListFragment.newInstance(1)
            else -> throw IllegalStateException("Incorrect Position")
        }
    }

    override fun getItemCount() = 2
}
