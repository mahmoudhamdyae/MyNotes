package com.mahmoudhamdyae.mynotes.database

class Repository {

    fun getAllNotes() = FirebaseNetwork().getAllNotes()

    fun saveNote(note: Note) {
        FirebaseNetwork().saveNote(note)
    }

    fun updateNote(note: Note) {
        FirebaseNetwork().updateNote(note)
    }

    fun delNote(noteId: String) {
        FirebaseNetwork().delNote(noteId)
    }
}