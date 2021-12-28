package com.leonardomotta.noteapp.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leonardomotta.noteapp.database.Note
import com.leonardomotta.noteapp.database.NoteDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteHomeViewModel( val database: NoteDatabaseDao, application: Application):AndroidViewModel(application) {

    val notes = database.getAllNotes()

    private val _navigateToNoteData = MutableLiveData<Long>()
    val navigateToNoteData
    get() = _navigateToNoteData

    fun onNoteClicked(id: Long){
        _navigateToNoteData.value = id
    }

    fun onNoteDataNavigated(){
        _navigateToNoteData.value = null
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }
}