package com.example.habit.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.*
import com.example.habit.adapters.HabitAdapter

import kotlinx.android.synthetic.main.habits_list_fragment.*


const val LOG_DEBUG = "Debug"
const val LOG_NETWORK = "Network"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_TYPE_FILTER = "typeFilter"
const val PASS_TYPE = "typeFilter"

class HabitsListFragment : Fragment() {

    private var typeFilterValue = 1

    private lateinit var habitAdapter: HabitAdapter

    private lateinit var viewModel: HabitsListViewModel


    companion object{
        // чтобы не передавать аргументы в конструктор
        fun newInstance(type: Int): HabitsListFragment{
            val bundle = Bundle()
            bundle.putInt(PASS_TYPE, type)
            val fragment = HabitsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(LOG_DEBUG, "onCreateView")
        viewModel = requireActivity().let { ViewModelProvider(it).get(HabitsListViewModel::class.java)}


        val args = arguments
        if (args != null) {
            // достаем аргументы из конструктора
            typeFilterValue = args.getInt(PASS_TYPE, 0)
        }

        return inflater.inflate(R.layout.habits_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(LOG_DEBUG, "onActivityCreated")

        Log.d(LOG_DEBUG, typeFilterValue.toString())

        val habitsList = ArrayList<Habit>()

        habitAdapter = HabitAdapter(habitsList, typeFilterValue)

        listOfHabits.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = habitAdapter

            //разделитель для элементов списка
            val divider = ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.habits_list_divider)
            val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(divider!!)
            listOfHabits.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHabitsFromServer()

        val habitsLiveData = HabitsData.getAllHabits()
        habitsLiveData.observe(viewLifecycleOwner, Observer { habits ->

            //val dataBase = HabitApp.instance.getDataBase().habitsDao()
            //habits.forEach { habit -> dataBase.deleteHabit(habit) }

            HabitApp.dataBaseSize = habits.size
            viewModel.setHabits(ArrayList(habits))
            viewModel.filter()
            viewModel.sort()
        })


        viewModel.getFilteredHabits().observe(viewLifecycleOwner, Observer { habits ->
            habitAdapter.habitsList = habits
            habitAdapter.notifyDataSetChanged()
            listOfHabits.layoutManager = LinearLayoutManager(activity)
            listOfHabits.adapter = habitAdapter
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_FOR_SAVING_TYPE_FILTER, typeFilterValue)
    }
}
