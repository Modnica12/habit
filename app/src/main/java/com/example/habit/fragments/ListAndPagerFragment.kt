package com.example.habit.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.habit.Communicator

import com.example.habit.R
import com.example.habit.adapters.HabitsListViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.android.synthetic.main.main_fragment.*

class ListAndPagerFragment : Fragment() {

    companion object {
        fun newInstance() = ListAndPagerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_DEBUG, "PAger onCreateView")
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        Log.d(LOG_DEBUG, "PAger ActivityCreated")

        val viewModel  = ViewModelProviders.of(this).get(HabitsListViewModel::class.java)


        // создаем адаптер для viewPager с 2 списками
        val adapter = HabitsListViewPagerAdapter(activity!!.supportFragmentManager, viewModel)

        habitsViewPager.adapter = adapter
        // добавляем pager к tab
        habitsTabLayout.setupWithViewPager(habitsViewPager)


        // создаем bottomSheet
        val transaction = activity!!.supportFragmentManager.beginTransaction()

        transaction.add(R.id.bottomContentContainer, BottomSheetFragment.newInstance(viewModel))
        transaction.commit()

    }

}
