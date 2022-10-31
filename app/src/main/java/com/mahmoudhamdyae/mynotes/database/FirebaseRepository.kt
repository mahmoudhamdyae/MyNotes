package com.mahmoudhamdyae.mynotes.database

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mahmoudhamdyae.mynotes.Utils

class FirebaseRepository() {

    private val userId = Utils().getUser()?.uid.toString()
    private val db = Firebase.firestore

    fun getAllNotes() = db.collection(userId)

    fun saveNote(note: Note) {
        val data = hashMapOf(
            "title" to note.title,
            "description" to note.description
        )
        db.collection(userId)
            .add(data)
            .addOnSuccessListener {document ->
                Log.d("haha", "DocumentSnapshot written with ID: ${document.id}")
            }
            .addOnFailureListener { e -> Log.w("haha", "Error writing document", e) }
    }

    fun updateNote(note: Note) {
        val data = hashMapOf(
            "title" to note.title,
            "description" to note.description
        )
        db.collection(userId).document(note.id)
            .set(data)
            .addOnSuccessListener {
                Log.d("haha", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w("haha", "Error updating document", e)
            }
    }

    fun delNote(noteId: String) {
        db.collection(userId).document(noteId)
            .delete()
            .addOnSuccessListener {
                Log.d("haha", "Document Deleted")
            }
            .addOnFailureListener { e ->
                Log.w("haha", "Error deleting document", e)
            }
    }
}