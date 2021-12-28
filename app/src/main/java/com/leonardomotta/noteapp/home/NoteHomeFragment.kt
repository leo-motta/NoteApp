package com.leonardomotta.noteapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leonardomotta.noteapp.R
import com.leonardomotta.noteapp.database.NoteDatabase
import com.leonardomotta.noteapp.databinding.FragmentNoteHomeBinding

class NoteHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        val binding: FragmentNoteHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_home, container, false)

        //ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val viewModelFactory = NoteHomeViewModelFactory(dataSource, application)
        val noteHomeViewModel = ViewModelProvider(this, viewModelFactory).get(NoteHomeViewModel::class.java)
        //ViewModel
        binding.noteHomeViewModel = noteHomeViewModel

        //Adapter
        val adapter = NoteAdapter(NoteAdapter.NoteListener{
            noteId -> noteHomeViewModel.onNoteClicked(noteId)
        })
        binding.noteList.adapter = adapter


        binding.lifecycleOwner = this

        //Mandar Lista
        noteHomeViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        noteHomeViewModel.navigateToNoteData.observe(viewLifecycleOwner, Observer {note_id ->
            note_id?.let {
                this.findNavController().navigate(NoteHomeFragmentDirections.actionNoteHomeFragmentToNoteDataFragment(note_id))
                noteHomeViewModel.onNoteDataNavigated()
            }
        })

        binding.newNoteButton.setOnClickListener() {
            this.findNavController().navigate(NoteHomeFragmentDirections.actionNoteHomeFragmentToNoteEditorFragment())
        }

        return binding.root
    }
}