package com.example.habit
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey var habitId: Int,
    @ColumnInfo val habitName: String?,
    @ColumnInfo val description: String?,
    @ColumnInfo val color: Int,
    @ColumnInfo val priority: Int,
    @ColumnInfo val type: Int,
    @ColumnInfo val quantity: Int,
    @ColumnInfo val period: Int
): Serializable