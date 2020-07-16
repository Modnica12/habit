package com.example.habit.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.data.LOG_DEBUG
import com.example.domain.DoneHabitToastType
import com.example.domain.Habit
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.coroutines.CoroutineContext
import com.example.domain.usecases.DoneHabitUseCase
import com.example.domain.usecases.GetHabitsUseCase
import com.example.domain.usecases.GetDoneHabitToastTypeUseCase
import com.example.domain.usecases.SaveHabitsFromServerUseCase
import com.example.habit.presentation.fragments.SortBy
import javax.inject.Inject


class HabitsListViewModel @Inject constructor(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val saveHabitsFromServerUseCase: SaveHabitsFromServerUseCase,
    private val doneHabitUseCase: DoneHabitUseCase,
    private val getToastContentTypeUseCase: GetDoneHabitToastTypeUseCase
) : ViewModel(), Serializable, CoroutineScope{

    val HTTP_NOT_FOUND = 404
    val HTTP_NOT_AUTH = 401
    val INTERNAL_SERVER_ERROR = 500

    private val mutableStringForFilter: MutableLiveData<String> = MutableLiveData()
    private val mutableSortBy: MutableLiveData<SortBy> = MutableLiveData()
    private var sortType: Int = 0
    val stringForFilter: LiveData<String> = mutableStringForFilter
    val sortBy: LiveData<SortBy> = mutableSortBy

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throw throwable }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            saveHabitsFromServerUseCase.saveHabitsFromServer()
        }
    }

    fun setStringForFilter(filterString: String){
        mutableStringForFilter.postValue(filterString)
    }

    fun setSortBy(sort: SortBy) {
        mutableSortBy.postValue(sort)
    }

    fun setSortType(type: Int){
        sortType = type
    }

    fun getSortType() = sortType

    @ExperimentalCoroutinesApi
    fun getAllHabits(): LiveData<List<Habit>>{
        return getHabitsUseCase.getHabits().asLiveData()
    }

    fun doneHabit(habit: Habit): DoneHabitToastType{
        viewModelScope.launch(Dispatchers.IO){
            doneHabitUseCase.doneHabit(habit)
        }
        Log.d(LOG_DEBUG, "EXCEEDED    ${habit.isExceeded()}")
        return getToastContentTypeUseCase.getDoneHabitToastType(habit.isExceeded(), habit.type)
    }
}
