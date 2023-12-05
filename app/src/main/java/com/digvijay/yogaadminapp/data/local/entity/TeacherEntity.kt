package com.digvijay.yogaadminapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeacherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
)