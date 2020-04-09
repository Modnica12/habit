package com.example.habit.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitsData

class DataInputViewModel : ViewModel() {
    private val mutableCurrentHabit: MutableLiveData<Habit> = MutableLiveData()
    private var currentPosition = 0

    init {

    }

    fun postCurrentHabit(habit: Habit){
        mutableCurrentHabit.value = habit
        Log.d(LOG_DEBUG, currentPosition.toString())
        Log.d(LOG_DEBUG, habit.toString())
        if (currentPosition == -1){
            HabitsData.addHabit(mutableCurrentHabit.value!!)
        }
        else
            HabitsData.changeHabitListAt(currentPosition, mutableCurrentHabit.value!!)
    }

    fun setCurrentPosition(position: Int){
        currentPosition = position
    }

}
