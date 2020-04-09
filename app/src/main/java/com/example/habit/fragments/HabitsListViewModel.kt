package com.example.habit.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habit.Habit
import java.io.Serializable

class HabitsListViewModel : ViewModel(), Serializable{
    private val mutableHabits: MutableLiveData<ArrayList<Habit>> = MutableLiveData()
    private val filteredHabits: MutableLiveData<ArrayList<Habit>> = MutableLiveData()
    private val stringForFilter: MutableLiveData<String> = MutableLiveData()
    private val sortBy: MutableLiveData<SortBy> = MutableLiveData()
    private val sortType: MutableLiveData<Int> = MutableLiveData()

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
                list.filter { habit -> habit.habitName!!.contains(stringForFilter.value!!) } as ArrayList<Habit>
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
                    else -> TODO()
                }
            else
                when (sortBy.value) {
                    SortBy.PRIORITY -> compareByDescending { it.priority }
                    else -> compareByDescending { it.priority }
                }

            // передаем компоратор, чтобы отсортировать список по нужному полю
            val sorted = list.sortedWith(comparator)
            filteredHabits.value = ArrayList(sorted)
        }
    }

}
