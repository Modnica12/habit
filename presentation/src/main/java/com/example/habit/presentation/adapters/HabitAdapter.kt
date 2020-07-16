package com.example.habit.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.data.KEY_FOR_HABIT
import com.example.data.LOG_DEBUG
import com.example.domain.DoneHabitToastType
import com.example.domain.Habit
import com.example.habit.R
import com.example.habit.presentation.viewmodels.HabitsListViewModel
import com.example.habit.presentation.fragments.SortBy
import kotlinx.android.synthetic.main.habit_row.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HabitAdapter(private val typeFilterValue: Int, private val viewModel: HabitsListViewModel) :
    RecyclerView.Adapter<HabitViewHolder>(){

    private var habitsList = listOf<Habit>()

    private var filteredHabits: List<Habit> = habitsList

    private fun filter(){
        filteredHabits = habitsList.filter { habit: Habit -> habit.type == typeFilterValue }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        filter()
        return HabitViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.habit_row, parent, false),
            viewModel
        )
    }

    override fun getItemCount(): Int {
        return filteredHabits.size
    }

    fun setHabits(habits: List<Habit>){
        habitsList = habits
        filter()
        notifyDataSetChanged()
    }

    fun filter(filterString: String){
        filter()
        Log.d(LOG_DEBUG, "STRING $filterString")
        filteredHabits = filteredHabits.filter { habit -> (habit.title ?: "").contains(filterString) }
        notifyDataSetChanged()
        Log.d(LOG_DEBUG,"FILTERED $filteredHabits")
    }

    fun sortFromSmallest(sortBy: SortBy) {
        filteredHabits = filteredHabits.sortedWith(when (sortBy) {
                SortBy.PRIORITY -> compareBy { it.priority }
                SortBy.DATE -> compareBy { it.date }
        })
        notifyDataSetChanged()
    }

    fun sortFromBiggest(sortBy: SortBy) {
        filteredHabits = filteredHabits.sortedWith(when (sortBy) {
            SortBy.PRIORITY -> compareByDescending { it.priority }
            SortBy.DATE -> compareByDescending { it.date }
        })
        Log.d(LOG_DEBUG, "SORTED   $filteredHabits")
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        //в listView нам бы пришлось создавать inflater (аналог holder)
        val habit = filteredHabits[position]

        holder.bind(habit)
    }



}

class HabitViewHolder(private val view: View, private val viewModel: HabitsListViewModel) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.habitName
    val times: TextView = view.timesInPeriod
    val priority: TextView = view.priority
    val type: ImageView = view.habitType
    val color: ImageView = view.habitColor
    val description: TextView = view.description
    val habitDoneButton = view.doneHabitButton

    val context = view.context

    fun bind(habit: Habit){
        name.text = habit.title
        times.text = context.resources.getString(R.string.frequency_text, habit.count, habit.frequency)
        priority.text = context.resources.getString(R.string.priority_text, habit.priority)
        description.text = habit.description

        if (habit.type == 0)
            type.setImageResource(R.drawable.ic_bad_habit)
        else
            type.setImageResource(R.drawable.ic_good_habit)

        color.setColorFilter(habit.color)

        view.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(KEY_FOR_HABIT, habit)
            Navigation.findNavController(view.context as Activity, R.id.nav_host_fragment_container)
                .navigate(R.id.dataInputFragment, bundle)
        }

        val rest = habit.getRest() - 1

        habitDoneButton.setOnClickListener {
            Toast.makeText(context, when (viewModel.doneHabit(habit)){
                DoneHabitToastType.BAD_EXCEED -> context.resources.getString(R.string.bad_exceed)
                DoneHabitToastType.BAD_LEFT -> context.resources.getString(R.string.bad_left, rest)
                DoneHabitToastType.GOOD_EXCEED -> context.resources.getString(R.string.good_exceed)
                DoneHabitToastType.GOOD_LEFT-> context.resources.getString(R.string.good_left, rest)
                else -> context.resources.getString(R.string.unknown_habit_done_type)
            }, Toast.LENGTH_SHORT).show()
        }
    }
}
