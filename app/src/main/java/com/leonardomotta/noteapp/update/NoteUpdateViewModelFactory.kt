package com.leonardomotta.noteapp.update

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardomotta.noteapp.database.NoteDatabaseDao

class NoteUpdateViewModelFactory(private val noteKey: Long,
                                 private val dataSource: NoteDatabaseDao,
                                 private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteUpdateViewModel::class.java)) {
            return NoteUpdateViewModel(noteKey, dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}