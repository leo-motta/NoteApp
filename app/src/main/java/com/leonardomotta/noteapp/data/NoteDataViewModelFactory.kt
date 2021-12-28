package com.leonardomotta.noteapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leonardomotta.noteapp.database.NoteDatabaseDao

class NoteDataViewModelFactory (private val noteKey: Long,
                                private val dataSource: NoteDatabaseDao) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteDataViewModel::class.java)) {
                return NoteDataViewModel(noteKey, dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }