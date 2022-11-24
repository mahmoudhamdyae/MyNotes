package com.mahmoudhamdyae.mynotes.editor

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahmoudhamdyae.mynotes.BaseApplication
import com.mahmoudhamdyae.mynotes.database.FirebaseApi
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

    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY]) as BaseApplication
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return EditorViewModel(
                    Repository(FirebaseApi()),
                    savedStateHandle
                ) as T
            }
        }
    }
}