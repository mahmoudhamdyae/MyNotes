package com.mahmoudhamdyae.mynotes.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.database.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>>
        get() = _notes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        getNotes2()
    }

    private fun getNotes2() {
        try {
            repository.getAllNotes().addSnapshotListener(EventListener { value, e ->
                if (e != null) {
                    Log.d("haha", "Listen failed.", e)
                    return@EventListener
                }
                val notesList : MutableList<Note> = mutableListOf()
                for (document in value!!) {
                    val noteItem = Note(document.id, document.get("title").toString(), document.get("description").toString())
                    Log.d("haha", "Get: $noteItem")
                    notesList.add(noteItem)
                }
                _notes.value = notesList
            })
        } catch (e: Exception) {
            _error.value = e.toString()
        }
    }
}