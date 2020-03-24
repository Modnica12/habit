package com.example.habit
import java.io.Serializable

data class Habit(
    val habitName: String?, val description: String?, val color: Int,
    val priority: Int, val type: Int, val quantity: Int, val period: Int): Serializable