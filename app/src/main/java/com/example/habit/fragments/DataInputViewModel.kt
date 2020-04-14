package com.example.habit.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitApp

class DataInputViewModel : ViewModel() {
    private val mutableCurrentHabit: MutableLiveData<Habit> = MutableLiveData()
    private val dataBase = HabitApp.instance.getDataBase().habitsDao()

    init {

    }

    fun postCurrentHabit(habit: Habit){
        mutableCurrentHabit.value = habit
        // берем позицию привычки
        val currentPosition = habit.habitId

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

    fun getHabitById(id: Int): LiveData<Habit>{
        val habit = dataBase.getBy(id)
        Log.d(LOG_DEBUG, "getbyid " + habit.value)
        return habit
    }

}
