package com.example.habit.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitsData

class HabitsListViewModel : ViewModel() {
    private val mutableHabits: MutableLiveData<ArrayList<Habit>> = MutableLiveData()
    val habits: LiveData<ArrayList<Habit>> = mutableHabits

    init {
        HabitsData.subscribe { habits -> mutableHabits.postValue(habits) }
    }

    fun getHabits(): MutableLiveData<ArrayList<Habit>>{
        return mutableHabits
    }

}
