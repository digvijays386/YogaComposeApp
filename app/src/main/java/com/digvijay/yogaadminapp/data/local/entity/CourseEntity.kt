package com.digvijay.yogaadminapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek

@Entity
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val dayOfWeek: String,
    val time: String,
    val duration: String,
    val price: Int = 40,
    val capacity: Int,
    val desc: String = "",
    val type: String = "Yoga",
    val instruction: String? = null,
    val openingHour: Int = 10
)
