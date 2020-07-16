package com.example.habit.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.data.LOG_DEBUG

import com.example.habit.R
import com.example.habit.adapters.HabitsListViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_fragment.*
import java.lang.IllegalStateException

class ListAndPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        habitsViewPager.adapter = HabitsListViewPagerAdapter(requireActivity() as AppCompatActivity)
        TabLayoutMediator(habitsTabLayout, habitsViewPager) { tab, position ->
            tab.text = when (position){
                0 -> "Bad"
                1 -> "Good"
                else -> throw IllegalStateException("Incorrect Position")
            }
        }.attach()
    }

}
