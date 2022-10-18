package com.mahmoudhamdyae.mynotes.catalog

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

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val _notes = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>>
        get() = _notes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val database = NoteDatabase.getInstance(application)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        coroutineScope.launch {
            try {
                database.noteDao().getAllNotes().collect() {
                    _notes.postValue(it)
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}