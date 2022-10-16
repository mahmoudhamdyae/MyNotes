package com.mahmoudhamdyae.mynotes.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param note new value to write
     */
    @Update
    suspend fun updateNote(note: Note)

    /**
     * Selects and returns the row that matches the supplied id.
     *
     * @param id key to match
     */
    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Long) : Flow<Note>

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by writing time in descending order.
     */
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNotes() : Flow<List<Note>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM note")
    suspend fun deleteAllNotes()

    /**
     * Delete the row that matches the supplied id
     */
    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNoteById(id: Long)
}