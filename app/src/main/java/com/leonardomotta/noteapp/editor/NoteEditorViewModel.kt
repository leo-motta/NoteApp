package com.leonardomotta.noteapp.editor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leonardomotta.noteapp.database.Note
import com.leonardomotta.noteapp.database.NoteDatabaseDao
import kotlinx.coroutines.launch

class NoteEditorViewModel(val database: NoteDatabaseDao, application: Application): AndroidViewModel(application) {

    //
    private val _navigateToNoteHome = MutableLiveData<Boolean>()
    val navigateToNoteHome: LiveData<Boolean>
        get() = _navigateToNoteHome


    //
    fun createNote(title: String, text: String){
        //
        if(title.isNotEmpty() && text.isNotEmpty()) {
            val note = Note(0,title,text)
            viewModelScope.launch {
                insertNote(note)
            }

            navigate()
        }
    }

    //
    private suspend fun insertNote(note: Note) {
        database.insert(note)
    }

    private fun navigate(){
        _navigateToNoteHome.value = true
    }
    //
    fun doneNavigating() {
        _navigateToNoteHome.value = false
    }


}