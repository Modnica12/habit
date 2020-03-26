package com.example.habit.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.Communicator
import com.example.habit.Habit
import com.example.habit.R
import com.example.habit.fragments.LOG_DEBUG
import kotlinx.android.synthetic.main.habit_row.view.*

class HabitAdapter(private val habits: ArrayList<Habit>) :
    RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.habit_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        //в listView нам бы пришлось создавать inflater (аналог holder)
        val habit = habits[position]
        Log.d(LOG_DEBUG, "BAD")
        holder.bind(habit)
        holder.itemView.setOnClickListener {
            Log.d(LOG_DEBUG, "clicked")
            val communicator = holder.itemView.context as Communicator
            communicator.passData(habit, position)
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
