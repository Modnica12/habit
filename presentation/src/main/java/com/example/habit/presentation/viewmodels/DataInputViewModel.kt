package com.example.habit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.Habit
import com.example.domain.usecases.GetHabitUseCase
import com.example.domain.usecases.SaveHabitUseCase
import kotlinx.coroutines.*
import java.security.cert.TrustAnchor
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DataInputViewModel @Inject constructor(
    private val saveHabitUseCase: SaveHabitUseCase,
    private val getHabitUseCase: GetHabitUseCase): ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throw throwable }

    private val mutableIsReady = MutableLiveData<Boolean>()
    val isReady: LiveData<Boolean> = mutableIsReady

    private suspend fun postHabit(habit: Habit){
        habit.date = (Date().time / 1000).toInt()
        saveHabitUseCase.updateHabit(habit, habit.uid == "")
        mutableIsReady.postValue(true)
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

}
