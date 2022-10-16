package com.mahmoudhamdyae.mynotes.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        dao = db.noteDao
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runBlocking {
        val note1 = Note(300,"Title Test", "Description Test")
        dao.insertNote(note1)
        val note2 : Flow<Note> = dao.getNoteById(note1.id)
        assertEquals(note1.title, note2.first().title)
    }
}