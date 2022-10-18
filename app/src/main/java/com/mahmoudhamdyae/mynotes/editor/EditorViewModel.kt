package com.mahmoudhamdyae.mynotes.editor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    val database = NoteDatabase.getInstance(application)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        _isFinished.value = false
        _selectedNote.value = note
    }

    fun saveNote(note: Note) {
        coroutineScope.launch {
            try {
                database.noteDao().insertNote(note)
                _isFinished.value = true
            } catch (e: Exception) {
                _error.value = note.title + e.toString()
            }
        }
    }

    fun closeFragment(){
        _isFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}