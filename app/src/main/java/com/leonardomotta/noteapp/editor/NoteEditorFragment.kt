package com.leonardomotta.noteapp.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leonardomotta.noteapp.R
import com.leonardomotta.noteapp.database.NoteDatabase
import com.leonardomotta.noteapp.databinding.FragmentNoteEditorBinding


class NoteEditorFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNoteEditorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_editor, container, false)

        //ViewModelFactory
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = NoteEditorViewModelFactory(dataSource, application)

        //ViewModel
        val noteEditorViewModel = ViewModelProvider(this, viewModelFactory).get(NoteEditorViewModel::class.java)

        binding.noteEditorViewModel = noteEditorViewModel
        binding.lifecycleOwner = this

        //melhorar
        binding.sendButton.setOnClickListener() {
            val title = binding.noteTitle.text.toString().trim()
            val text = binding.noteText.text.toString().trim()
            noteEditorViewModel.createNote(title,text)
        }

        noteEditorViewModel.navigateToNoteHome.observe(viewLifecycleOwner, Observer { doneNavigating ->
            if(doneNavigating){
                this.findNavController().navigate(NoteEditorFragmentDirections.actionNoteEditorFragmentToNoteHomeFragment())
                noteEditorViewModel.doneNavigating()
            }

        })


        return binding.root
    }


}