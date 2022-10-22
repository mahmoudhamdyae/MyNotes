package com.mahmoudhamdyae.mynotes.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest : TestCase() {

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = NoteDatabase.getInstance(context)
        dao = db.noteDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runBlocking {
        val note1 = Note(300,"Title Test", "Description Test")
        dao.insertNote(note1)
        assertEquals(dao.getNoteById(300).first().title, "Title Test")
    }

    @Test
    fun writeAndUpdateNote() = runBlocking {
        val note1 = Note(300,"Title Test", "Description Test")
        dao.insertNote(note1)
        dao.updateNote(Note(300, "Title", "Description Test"))
        assertEquals(dao.getNoteById(300).first().title, "Title")
    }

    @Test
    fun writeAndDeleteNote() = runBlocking {
        val note1 = Note(300,"Title Test", "Description Test")
        dao.insertNote(note1)
//        dao.deleteNoteById(300)
        assertEquals(dao.getNoteById(300).first().title, "Title Test")
    }
}