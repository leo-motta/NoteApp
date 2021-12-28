package com.leonardomotta.noteapp.update

import android.app.Application
import androidx.lifecycle.*
import com.leonardomotta.noteapp.database.Note
import com.leonardomotta.noteapp.database.NoteDatabaseDao
import kotlinx.coroutines.launch

class NoteUpdateViewModel(private val noteKey: Long = 0L,
                          dataSource: NoteDatabaseDao,
                          application: Application): AndroidViewModel(application) {

    //
    private val _navigateToNoteHome = MutableLiveData<Boolean>()
    val navigateToNoteHome: LiveData<Boolean>
        get() = _navigateToNoteHome

    private val _navigateToNoteData = MutableLiveData<Boolean>()
    val navigateToNoteData: LiveData<Boolean>
        get() = _navigateToNoteData


    val database = dataSource

    private val note = MediatorLiveData<Note>()

    fun getNote() = note

    init {
        note.addSource(database.getNoteWithId(noteKey), note::setValue)
    }

    fun updateNote(sameKey: Long, newTitle:String ,newText:String){

        if(newTitle.isNotEmpty() && newText.isNotEmpty()) {

            val newNote = Note(sameKey,newTitle,newText)

            viewModelScope.launch {
                update(newNote)
            }
            navigateToNoteData()
        }
    }

    fun deleteNote(key: Long){

        viewModelScope.launch {
            delete(key)
        }
        navigateToNoteHome()
    }


    private suspend fun update(note: Note) {
        database.update(note)
    }

    private suspend fun delete(key: Long){
        database.delete(key)
    }

    //depois do delete
    private fun navigateToNoteHome(){
        _navigateToNoteHome.value = true
    }

    fun doneNavigatingToNoteHome() {
        _navigateToNoteHome.value = false
    }

    //Depois do update
    private fun navigateToNoteData(){
        _navigateToNoteData.value = true
    }

    fun doneNavigatingToNoteData() {
        _navigateToNoteData.value = false
    }

}
