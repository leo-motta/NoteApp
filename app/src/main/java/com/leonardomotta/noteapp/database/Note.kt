package com.leonardomotta.noteapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
        @PrimaryKey(autoGenerate = true)
        var noteId: Long,

        @ColumnInfo(name = "title")
        var title: String? = null,

        @ColumnInfo(name = "text")
        var text: String? = null
)