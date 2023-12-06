package com.devhp.firstcompose.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.devhp.firstcompose.data.NotesDataSource
import com.devhp.firstcompose.model.Note

@RequiresApi(Build.VERSION_CODES.O)
class NoteViewModel : ViewModel() {
     val noteList = mutableListOf<Note>()

    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }

    fun addNote(note: Note) {
        noteList.add(note)
    }

    fun removeNote(note: Note) {
        noteList.remove(note)
    }
}