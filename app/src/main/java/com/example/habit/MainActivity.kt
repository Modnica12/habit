package com.example.habit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

const val LOG_DEBUG = "Debug"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_HABITS_LIST = "habits"

class MainActivity : AppCompatActivity(), OnHabitItemClickListener {

    private var habitsList = ArrayList<Habit>()
    private val CREATE_HABIT_ID = 1
    private val CHANGE_HABIT_ID = 2
    private lateinit var habitAdapter: HabitAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val habit1 = Habit("Бег", "За зож", Color.parseColor("#9C0007"),3, 1, 1, 7)
        val habit2 = Habit("Курение", "Бросить", Color.parseColor("#028F9C"),5, 0, 10, 5)
        habitsList.add(habit1)
        habitsList.add(habit2)
        habitAdapter = HabitAdapter(habitsList, this)
        listOfHabits.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listOfHabits.adapter = habitAdapter
        val divider = getDrawable(R.drawable.habits_list_divider)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        if (divider != null) {
            dividerItemDecoration.setDrawable(divider)
        }
        listOfHabits.addItemDecoration(dividerItemDecoration)


        addHabitButton.setOnClickListener {
            val intent = Intent(this, DataInputActivity::class.java)
            startActivityForResult(intent, CREATE_HABIT_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_HABIT_ID) {
            Log.d(LOG_DEBUG, "Created")
            if (resultCode == Activity.RESULT_OK) {
                val newHabit = data?.getSerializableExtra(KEY_FOR_HABIT) as Habit
                habitsList.add(newHabit)
            }
        }
        if (requestCode == CHANGE_HABIT_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(LOG_DEBUG, "Change")
                val newHabit = data?.getSerializableExtra(KEY_FOR_HABIT) as Habit
                val id = data.getIntExtra(ID_KEY, 0)
                habitsList[id] = newHabit
            }
        }
        habitAdapter.notifyDataSetChanged()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_FOR_SAVING_HABITS_LIST, habitsList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedHabits = savedInstanceState.getSerializable(KEY_FOR_SAVING_HABITS_LIST)
        habitsList = savedHabits as ArrayList<Habit>
        habitAdapter = HabitAdapter(habitsList, this)
        listOfHabits.adapter = habitAdapter
    }

    override fun onItemClick(habit: Habit, position: Int) {
        val intent = Intent(this, DataInputActivity::class.java)
        intent.putExtra(KEY_FOR_HABIT, habit)
        intent.putExtra(ID_KEY, position)
        startActivityForResult(intent, CHANGE_HABIT_ID)
    }
}
