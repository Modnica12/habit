package com.example.habit.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitApp

class DataInputViewModel : ViewModel() {
    private val mutableCurrentHabit: MutableLiveData<Habit> = MutableLiveData()

    init {

    }

    fun postCurrentHabit(habit: Habit){
        mutableCurrentHabit.value = habit
        // берем позицию привычки
        val currentPosition = habit.habitId
        val dataBase = HabitApp.instance.getDataBase().habitsDao()

        // если новая, то присваиваем позицию и добавляем в конец
        if (currentPosition == -1){
            val size = HabitApp.dataBaseSize
            habit.habitId = size
            mutableCurrentHabit.value = habit
            dataBase.addHabit(mutableCurrentHabit.value!!)
        }
        else // если изменяем существующую
            dataBase.updateHabit(mutableCurrentHabit.value!!)
    }


}
