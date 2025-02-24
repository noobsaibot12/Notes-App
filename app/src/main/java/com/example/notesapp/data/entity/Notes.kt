package com.example.notesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "notes" )
data class Notes (

    @PrimaryKey( autoGenerate = true )
    val id: Int = 0,

    val title: String,
    val description: String,
    val timestamp: String,
    val image: String,
    val isSaved: Boolean,
    val isCompleted: Boolean

)