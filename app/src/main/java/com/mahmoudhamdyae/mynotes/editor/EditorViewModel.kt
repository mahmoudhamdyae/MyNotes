package com.mahmoudhamdyae.mynotes.editor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.database.FirebaseNetwork
import com.mahmoudhamdyae.mynotes.database.Repository

class EditorViewModel (note: Note, application: Application) : AndroidViewModel(application) {

    private val _selectedNote = MutableLiveData<Note>()
    val selectedNote: LiveData<Note>
        get() = _selectedNote

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val notesRepository = Repository()

    init {
        _isFinished.value = false
        _selectedNote.value = note
    }

    fun saveNote(note: Note) {
        try {
            notesRepository.saveNote(note)
            _isFinished.value = true
        } catch (e: Exception) {
            _error.value = "Error: $e"
        }
    }

    fun updateNote(note: Note) {
        try {
            notesRepository.updateNote(note)
            _isFinished.value = true
        } catch (e: Exception) {
            _error.value = "Error: $e"
        }
    }

    fun delNote(noteId: String) {
        try {
            notesRepository.delNote(noteId)
            _isFinished.value = true
        } catch (e: Exception){
            _error.value = "Error: $e"
        }
    }

    fun closeFragment(){
        _isFinished.value = false
    }
}