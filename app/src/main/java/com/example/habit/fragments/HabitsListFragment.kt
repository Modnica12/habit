package com.example.habit.fragments

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.*

import kotlinx.android.synthetic.main.habits_list_fragment.*


const val LOG_DEBUG = "Debug"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_HABITS_LIST = "habits"
const val ID_KEY = "ID"

var habitsList = ArrayList<Habit>()

class HabitsListFragment : Fragment() {

    private val habitAdapter = HabitAdapter(habitsList)
    lateinit var communicator: Communicator

    companion object {
        fun newInstance() = HabitsListFragment()
    }

    private lateinit var viewModel: HabitsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.habits_list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HabitsListViewModel::class.java)
        val args = arguments
        if (args != null) {
            val habit = args.get(KEY_FOR_HABIT)
            if (habit != null) {
                Log.d(LOG_DEBUG, habit.toString())
                val id = args.get(ID_KEY) as Int
                if (id == -1)
                    habitsList.add(habit as Habit)
                else
                    habitsList[id] = habit as Habit
                args.remove(KEY_FOR_HABIT)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listOfHabits.apply {
            adapter = habitAdapter
            layoutManager = LinearLayoutManager(activity)
            //разделитель для элементов списка
            val divider = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.habits_list_divider)
            val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(divider!!)
            listOfHabits.addItemDecoration(dividerItemDecoration)
        }

        communicator = activity as Communicator

        addHabitButton.setOnClickListener {
            communicator.startNewFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
