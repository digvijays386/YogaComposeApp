package com.digvijay.yogaadminapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val dayOfWeek: String,
    val time: String,
    val date: String,
    val duration: String,
    val price: Int,
    val capacity: Int,
    val desc: String = "",
    val type: String = "Yoga",
)
