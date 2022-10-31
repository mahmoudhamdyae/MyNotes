package com.mahmoudhamdyae.mynotes.catalog

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.EventListener
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.database.FirebaseRepository

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val _notes = MutableLiveData<List<Note>>()
    val notes : LiveData<List<Note>>
        get() = _notes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val notesRepository = FirebaseRepository()

    init {
        getNotes2()
    }

    private fun getNotes2() {
        try {
            notesRepository.getAllNotes().addSnapshotListener(EventListener { value, e ->
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