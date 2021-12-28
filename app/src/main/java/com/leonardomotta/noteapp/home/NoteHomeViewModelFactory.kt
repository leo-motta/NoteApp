package com.leonardomotta.noteapp.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardomotta.noteapp.database.NoteDatabaseDao

class NoteHomeViewModelFactory(
        private val dataSource: NoteDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteHomeViewModel::class.java)) {
            return NoteHomeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}