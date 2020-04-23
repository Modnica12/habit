package com.example.habit.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitsData
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*
import kotlin.coroutines.CoroutineContext


class DataInputViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throw throwable }

    private suspend fun postHabit(habit: Habit){
        habit.date = (Date().time / 1000).toInt()
        // если новая, то присваиваем позицию и добавляем в конец
        if (habit.uid == "") {
            val uid = updateServerHabit(habit)
            habit.uid = uid ?: ""
            HabitsData.addHabit(habit)
        } else // если изменяем существующую
            updateServerHabit(habit)
            HabitsData.updateHabit(habit)
    }

    fun postCurrentHabit(habit: Habit){
        launch {
            withContext(Dispatchers.IO){
                postHabit(habit)
            }
        }
    }

    private suspend fun updateServerHabit(habit: Habit): String?{
        return try {
            HabitsData.serverPut(habit)
        } catch (e: HttpException){
            Log.d(LOG_NETWORK, "GET: Retry to request")
            delay(MILLISECONDS)
            updateServerHabit(habit)
            null
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun getHabitById(uid: String): LiveData<Habit>{
        return HabitsData.getBy(uid)
    }

}
