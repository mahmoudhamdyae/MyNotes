package com.mahmoudhamdyae.mynotes.database

import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseApi: FirebaseApi
) {

    fun getAllNotes() = firebaseApi.getAllNotes()

    fun saveNote(note: Note) {
        firebaseApi.saveNote(note)
    }

    fun updateNote(note: Note) {
        firebaseApi.updateNote(note)
    }

    fun delNote(noteId: String) {
        firebaseApi.delNote(noteId)
    }
}