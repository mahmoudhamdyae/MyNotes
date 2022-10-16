package com.mahmoudhamdyae.mynotes.editor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.mynotes.database.Note

class EditorViewModelFactory (
    private val note: Note,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditorViewModel::class.java)) {
            return EditorViewModel(note, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}