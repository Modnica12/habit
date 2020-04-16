package com.example.habit.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitApp
import com.example.habit.HabitsData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DataInputViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throw throwable }

    private fun postHabit(habit: Habit){
        // берем позицию привычки
        val currentPosition = habit.habitId

        // если новая, то присваиваем позицию и добавляем в конец
        if (currentPosition == -1){
            val size = HabitApp.dataBaseSize
            habit.habitId = size
            HabitsData.addHabit(habit)
        }
        else // если изменяем существующую
            HabitsData.updateHabit(habit)
    }

    fun postCurrentHabit(habit: Habit){
        launch {
            withContext(Dispatchers.IO){
                postHabit(habit)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun getHabitById(id: Int): LiveData<Habit>{
        val habit = HabitsData.getBy(id)
        Log.d(LOG_DEBUG, "getbyid " + habit.value)
        return habit
    }

}
