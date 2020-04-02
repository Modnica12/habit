package com.example.habit.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.*
import com.example.habit.adapters.HabitAdapter

import kotlinx.android.synthetic.main.habits_list_fragment.*


const val LOG_DEBUG = "Debug"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_HABITS_LIST = "habits"
const val KEY_FOR_SAVING_TYPE_FILTER = "typeFilter"
const val ID_KEY = "ID"
const val PASS_TYPE = "typeFilter"

class HabitsListFragment : Fragment() {

    private var habitsList = ArrayList<Habit>()

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

        viewModel = ViewModelProviders.of(this).get(HabitsListViewModel::class.java)    
        return inflater.inflate(R.layout.habits_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(LOG_DEBUG, "onActivityCreated")

        val args = arguments
        if (args != null) {
                // достаем аргументы из конструктора
                typeFilterValue = args.getInt(PASS_TYPE, 0)
            }

        Log.d(LOG_DEBUG, typeFilterValue.toString())

        habitAdapter = HabitAdapter(habitsList, typeFilterValue)

        listOfHabits.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = habitAdapter
            //разделитель для элементов списка
            val divider = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.habits_list_divider)
            val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(divider!!)
            listOfHabits.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHabits().observe(viewLifecycleOwner, Observer { habits ->
            habitsList = habits
            Log.d(LOG_DEBUG, "OBSERVER " + habits.toString())
            habitAdapter = HabitAdapter(habitsList, typeFilterValue)
            listOfHabits.layoutManager = LinearLayoutManager(activity)
            listOfHabits.adapter = habitAdapter
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_FOR_SAVING_TYPE_FILTER, typeFilterValue)
    }
}
