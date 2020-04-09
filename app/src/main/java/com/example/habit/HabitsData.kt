package com.example.habit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.habit.fragments.LOG_DEBUG

typealias subscriber = (ArrayList<Habit>) -> Unit

object HabitsData{
    private val habitsList = ArrayList<Habit>()
    private val subscribers = ArrayList<subscriber>()


    fun addHabit(habit: Habit){

        Log.d(LOG_DEBUG, "ADDED")
        habitsList.add(habit)
        subscribers.forEach{
            it(habitsList)
        }
    }

    fun changeHabitListAt(position: Int, habit: Habit){
        habitsList[position] = habit
        subscribers.forEach{
            it(habitsList)
        }
    }

    fun subscribe(action: subscriber){
        subscribers.add(action)
    }
}