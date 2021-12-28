package com.leonardomotta.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDatabaseDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE noteId = :key")
    fun getNoteWithId(key: Long): LiveData<Note>

    @Query("DELETE FROM note_table")
    suspend fun clear()

    @Query("DELETE FROM note_table where noteId = :key")
    suspend fun delete(key: Long)
}