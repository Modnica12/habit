package com.example.habit.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.Communicator
import com.example.habit.Habit
import com.example.habit.R
import kotlinx.android.synthetic.main.habit_row.view.*

class HabitAdapter(var habitsList: ArrayList<Habit>, private val typeFilterValue: Int) :
    RecyclerView.Adapter<HabitViewHolder>(){

    var filteredHabits = ArrayList<Habit>()

    private fun filter(){
        val badHabits= habitsList.filter { habit: Habit -> habit.type == typeFilterValue } as ArrayList<Habit>
        filteredHabits = badHabits
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        filter()
        return HabitViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.habit_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        filter()
        return filteredHabits.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        filter()
        //в listView нам бы пришлось создавать inflater (аналог holder)
        val habit = filteredHabits[position]

        holder.bind(habit)
        holder.itemView.setOnClickListener {
            val communicator = holder.itemView.context as Communicator
            communicator.passData(habit)
        }
    }

}

class HabitViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.habitName
    val times: TextView = view.timesInPeriod
    val priority: TextView = view.priority
    val type: ImageView = view.habitType
    val color: ImageView = view.habitColor
    val description: TextView = view.description

    @SuppressLint("SetTextI18n")
    fun bind(habit: Habit){
        name.text = habit.habitName
        times.text = habit.quantity.toString() + " раз в " + habit.period + " дней"
        priority.text = "приоритет: " + habit.priority
        description.text = habit.description

        if (habit.type == 0)
            type.setImageResource(R.drawable.ic_bad_habit)
        else
            type.setImageResource(R.drawable.ic_good_habit)

        color.setColorFilter(habit.color)
    }
}
