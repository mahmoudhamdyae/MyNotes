package com.mahmoudhamdyae.mynotes.editor

import androidx.lifecycle.*
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.database.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val note = EditorFragmentArgs.fromSavedStateHandle(savedStateHandle).note

    private val _selectedNote = MutableLiveData<Note>()
    val selectedNote: LiveData<Note>
        get() = _selectedNote

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        _isFinished.value = false
        _selectedNote.value = note
    }

    fun saveNote(note: Note) {
        try {
            repository.saveNote(note)
            _isFinished.value = true
        } catch (e: Exception) {
            _error.value = "Error: $e"
        }
    }

    fun updateNote(note: Note) {
        try {
            repository.updateNote(note)
            _isFinished.value = true
        } catch (e: Exception) {
            _error.value = "Error: $e"
        }
    }

    fun delNote(noteId: String) {
        try {
            repository.delNote(noteId)
            _isFinished.value = true
        } catch (e: Exception){
            _error.value = "Error: $e"
        }
    }

    fun closeFragment(){
        _isFinished.value = false
    }
}