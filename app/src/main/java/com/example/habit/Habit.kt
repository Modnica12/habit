package com.example.habit
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "habits")
data class Habit(
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo var date: Int,
    @ColumnInfo val description: String?,
    @ColumnInfo val frequency: Int,
    @ColumnInfo val priority: Int,
    @ColumnInfo val title: String?,
    @ColumnInfo val type: Int,
    @PrimaryKey var uid: String
):Serializable

data class HabitUID(
    val uid: String
):Serializable
