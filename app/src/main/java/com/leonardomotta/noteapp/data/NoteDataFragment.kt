package com.leonardomotta.noteapp.data

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
import com.leonardomotta.noteapp.databinding.FragmentNoteDataBinding


class NoteDataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNoteDataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note_data, container, false)
        val application = requireNotNull(this.activity).application

        //  val arguments = NoteDataFragmentArgs.fromBundle(arguments!!)
        val arguments = NoteDataFragmentArgs.fromBundle(requireArguments())

        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        val viewModelFactory = NoteDataViewModelFactory(arguments.noteKey, dataSource)

        val noteDataViewModel = ViewModelProvider(this, viewModelFactory).get(NoteDataViewModel::class.java)

        binding.noteDataViewModel = noteDataViewModel

        binding.lifecycleOwner = this

        //Melhorar
        binding.editButton.setOnClickListener(){
            noteDataViewModel.onClick()
        }

        noteDataViewModel.navigateToNoteUpdate.observe(viewLifecycleOwner, Observer { done ->
            if(done) {
                this.findNavController().navigate(NoteDataFragmentDirections.actionNoteDataFragmentToNoteUpdateFragment(arguments.noteKey))
                noteDataViewModel.doneNavigatingToNoteUpdate()
            }

        })


        return binding.root
    }
}