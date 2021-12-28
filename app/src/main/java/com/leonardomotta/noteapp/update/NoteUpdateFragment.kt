package com.leonardomotta.noteapp.update

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
import com.leonardomotta.noteapp.databinding.FragmentNoteDataBinding
import com.leonardomotta.noteapp.databinding.FragmentNoteUpdateBinding


class NoteUpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        val binding: FragmentNoteUpdateBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_note_update, container, false)
        //ViewModelFactory
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        //Arguments
        val arguments = NoteUpdateFragmentArgs.fromBundle(requireArguments())

        val key = arguments.noteKey

        val viewModelFactory = NoteUpdateViewModelFactory(key,dataSource, application)

        //ViewModel
        val noteUpdateViewModel = ViewModelProvider(this, viewModelFactory).get(NoteUpdateViewModel::class.java)

        //
        binding.noteUpdateViewModel = noteUpdateViewModel
        //
        binding.lifecycleOwner = this

        binding.sendButtonUpdate.setOnClickListener() {
            val title = binding.noteTitleUpdate.text.toString().trim()
            val text = binding.noteTextUpdate.text.toString().trim()
            noteUpdateViewModel.updateNote(key,title,text)
        }

        binding.deleteButtonUpdate.setOnClickListener() {
            noteUpdateViewModel.deleteNote(key)
        }

        noteUpdateViewModel.navigateToNoteData.observe(viewLifecycleOwner, Observer { doneNavigating ->
            if(doneNavigating) {
                this.findNavController().navigate(NoteUpdateFragmentDirections.actionNoteUpdateFragmentToNoteDataFragment(key))
                noteUpdateViewModel.doneNavigatingToNoteData()
            }
        })

        noteUpdateViewModel.navigateToNoteHome.observe(viewLifecycleOwner, Observer { done ->
            if(done) {
                this.findNavController().navigate(NoteUpdateFragmentDirections.actionNoteUpdateFragmentToNoteHomeFragment())
                noteUpdateViewModel.doneNavigatingToNoteHome()
            }
        })



        return binding.root
    }
}