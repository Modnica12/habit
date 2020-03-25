package com.example.habit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.habit_row.view.*

class HabitAdapter(private val habits: ArrayList<Habit>) :
    RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.habit_row, parent, false))
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        //в listView нам бы пришлось создавать inflater (аналог holder)
        val habit = habits[position]
        holder.bind(habit)
        holder.itemView.setOnClickListener {
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
