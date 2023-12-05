package com.digvijay.yogaadminapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ClassInstanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val courseId: Int,
    val date: String,
    val teacher: String,
    val comments: String?
)