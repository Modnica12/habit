package com.example.domain

import android.util.Log
import androidx.room.TypeConverter

class HabitDoneConverter{

    @TypeConverter
    fun fromDates(dates: List<Int>): String{
        return dates.joinToString(", ")
    }

    @TypeConverter
    fun toDates(dates: String): List<Int?>{
        return if (dates == "") listOf() else dates.split(", ").map { it.toInt() }
    }
}