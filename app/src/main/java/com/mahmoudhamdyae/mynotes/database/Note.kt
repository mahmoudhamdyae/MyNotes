package com.mahmoudhamdyae.mynotes.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Note(
    val id : String = "",
    var title: String = "",
    val description: String = ""
) : Parcelable
