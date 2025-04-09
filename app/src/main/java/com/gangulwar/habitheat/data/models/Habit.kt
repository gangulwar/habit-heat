package com.gangulwar.habitheat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val emoji: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val description:String? = null
)