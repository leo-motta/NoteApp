package com.leonardomotta.noteapp.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.leonardomotta.noteapp.database.Note

class BindingUtils {

    @BindingAdapter("noteTitleString")
    fun TextView.setNoteTileString(item: Note?) {
        item?.let {
           // text =
        }
    }
}