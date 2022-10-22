package com.mahmoudhamdyae.mynotes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    var title: String = "",
    val description: String = ""
)
