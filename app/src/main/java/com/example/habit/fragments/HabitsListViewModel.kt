package com.example.habit.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import com.example.habit.HabitApp
import com.example.habit.HabitsData
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

val MILLISECONDS = 5000L

class HabitsListViewModel() : ViewModel(), Serializable, CoroutineScope{

    val HTTP_NOT_FOUND = 404
    val HTTP_NOT_AUTH = 401
    val INTERNAL_SERVER_ERROR = 500

    private val mutableHabits: MutableLiveData<ArrayList<Habit>> = MutableLiveData()
    private val filteredHabits: MutableLiveData<ArrayList<Habit>> = MutableLiveData()
    private val stringForFilter: MutableLiveData<String> = MutableLiveData()
    private val sortBy: MutableLiveData<SortBy> = MutableLiveData()
    private val sortType: MutableLiveData<Int> = MutableLiveData()

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable -> throw throwable }

    init {
        setStringForFilter("")
    }

    private fun getHabits() = mutableHabits

    fun setHabits(habits: ArrayList<Habit>){
        mutableHabits.value = habits
    }

    fun getFilteredHabits() = filteredHabits

    fun setStringForFilter(filterString: String){
        // строка для фильтрации списка
        stringForFilter.value = filterString
    }

    fun setSortBy(sort: SortBy) {
        // по чему сортируем
        sortBy.value = sort
    }

    fun setSortType(type: Int){
        // по возростанию / убыванию
        sortType.value = type
    }

    fun filter(){
        // фильтруем список, если текст изменился
        val list = getHabits().value
        if (list!= null) {
            filteredHabits.value =
                list.filter { habit -> habit.title!!.contains(stringForFilter.value!!) } as ArrayList<Habit>
        }
    }

    fun sort(){
        val list = getFilteredHabits().value

        if(list != null){
            // компоратор, который в зависимости от enum'а будет сравнивать по какому-то из полей
            // + либо берем по убыванию, либо по возрастанию
            val comparator: Comparator<Habit> = if (sortType.value == SORT_FROM_SMALLEST)
                when (sortBy.value) {
                    SortBy.PRIORITY -> compareBy { it.priority }
                    SortBy.DATE -> compareBy { it.date }
                    else -> TODO()
                }
            else
                when (sortBy.value) {
                    SortBy.PRIORITY -> compareByDescending { it.priority }
                    SortBy.DATE -> compareByDescending { it.date }
                    else -> compareByDescending { it.date }
                }

            // передаем компоратор, чтобы отсортировать список по нужному полю
            val sorted = list.sortedWith(comparator)
            filteredHabits.value = ArrayList(sorted)
        }
    }

    fun getHabitsFromServer() {
        val cm = HabitApp.instance.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork= cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected)
            launch {
                withContext(Dispatchers.IO){
                    try {
                        HabitsData.updateListOfHabits(HabitsData.serverGet())
                    }
                    catch (e: HttpException){
                        when(e.code()){
                            HTTP_NOT_FOUND -> Log.d(LOG_NETWORK, "GET: Page Not Found")
                            HTTP_NOT_AUTH -> Log.d(LOG_NETWORK, "GET: Not Authorized")
                            INTERNAL_SERVER_ERROR-> Log.d(LOG_NETWORK, "GET: Internal Server Error")
                            else -> Log.d(LOG_NETWORK, "GET: Unknown Error")
                        }
                        Log.d(LOG_NETWORK, "GET: Retry to request")
                        delay(MILLISECONDS)
                        getHabitsFromServer()
                    }
                }
            }
        else
        {
            Log.d(LOG_NETWORK, "No Internet Connection")
            launch {
                withContext(Dispatchers.IO) {
                    Log.d(LOG_NETWORK, "GET: Retry to request")
                    delay(MILLISECONDS)
                    getHabitsFromServer()
                }
            }
        }
    }
}
