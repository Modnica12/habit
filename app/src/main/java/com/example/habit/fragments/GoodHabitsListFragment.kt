package com.example.habit.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.*
import com.example.habit.adapters.HabitAdapter

import kotlinx.android.synthetic.main.habits_list_fragment.*


const val LOG_DEBUG = "Debug"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_HABITS_LIST = "habits"
const val ID_KEY = "ID"

var goodHabitsList = ArrayList<Habit>()

class GoodHabitsListFragment : Fragment() {

    private val habitAdapter = HabitAdapter(goodHabitsList)
    lateinit var communicator: Communicator

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
                val id = args.get(ID_KEY) as Int
                if (id == -1)
                    goodHabitsList.add(habit as Habit)
                else
                    goodHabitsList[id] = habit as Habit
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
            communicator.startNewFragment(DataInputFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun addHabit(habit: Habit){
        goodHabitsList.add(habit)
    }

    fun changeHabitListAt(position: Int, habit: Habit){
        Log.d(LOG_DEBUG, "CHANGED")
        Log.d(LOG_DEBUG, habit.toString())
        goodHabitsList[position] = habit
    }

}