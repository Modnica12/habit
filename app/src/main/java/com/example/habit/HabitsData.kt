package com.example.habit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.habit.fragments.LOG_DEBUG

typealias subscriber = (ArrayList<Habit>) -> Unit

object HabitsData{
    private val habitsList = ArrayList<Habit>()
    private val subscribers = ArrayList<subscriber>()


    fun addHabit(habit: Habit){
        habitsList.add(habit)
        subscribers.forEach{
            it(habitsList)
        }
    }

    fun changeHabitListAt(position: Int, habit: Habit){
        Log.d(LOG_DEBUG, "CHANGED")
        Log.d(LOG_DEBUG, position.toString())
        Log.d(LOG_DEBUG, habit.toString())
        habitsList[position] = habit
        subscribers.forEach{
            it(habitsList)
        }
    }

    fun subscribe(action: subscriber){
        subscribers.add(action)
    }
}