package com.example.tsansroom.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book (

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
    )