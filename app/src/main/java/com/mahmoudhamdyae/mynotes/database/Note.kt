package com.mahmoudhamdyae.mynotes.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val title: String = "",
    val description: String = ""
) : Parcelable
