package com.leonardomotta.noteapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonardomotta.noteapp.database.Note
import com.leonardomotta.noteapp.database.NoteDatabaseDao
import javax.sql.DataSource

class NoteDataViewModel(private val noteKey: Long = 0L,
                       dataSource: NoteDatabaseDao): ViewModel() {

    private val _navigateToNoteUpdate = MutableLiveData<Boolean>()
    val navigateToNoteUpdate: LiveData<Boolean>
        get() = _navigateToNoteUpdate

    val database = dataSource

    private val note = MediatorLiveData<Note>()

    fun getNote() = note

    init {
        note.addSource(database.getNoteWithId(noteKey), note::setValue)
    }

    fun onClick(){
        navigateToNoteUpdate()
    }

    private fun navigateToNoteUpdate(){
        _navigateToNoteUpdate.value = true
    }

    fun doneNavigatingToNoteUpdate() {
        _navigateToNoteUpdate.value = false
    }


}