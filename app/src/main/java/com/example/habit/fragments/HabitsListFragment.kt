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
const val PASS_VIEWMODEL = "passViewModel"

class HabitsListFragment : Fragment() {

    private var typeFilterValue = 1

    private lateinit var habitAdapter: HabitAdapter

    private lateinit var viewModel: HabitsListViewModel


    companion object{
        // чтобы не передавать аргументы в конструктор
        fun newInstance(type: Int, viewModel: HabitsListViewModel): HabitsListFragment{
            val bundle = Bundle()
            bundle.putInt(PASS_TYPE, type)
            bundle.putSerializable(PASS_VIEWMODEL, viewModel)
            val fragment = HabitsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(LOG_DEBUG, "onCreateView")

        val args = arguments
        if (args != null) {
            // достаем аргументы из конструктора
            typeFilterValue = args.getInt(PASS_TYPE, 0)
            viewModel = args.get(PASS_VIEWMODEL) as HabitsListViewModel
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
            val divider = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.habits_list_divider)
            val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(divider!!)
            listOfHabits.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFilteredHabits().observe(viewLifecycleOwner, Observer { habits ->
            Log.d(LOG_DEBUG, "OBSERVER $typeFilterValue $habits")
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
