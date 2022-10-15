package com.example.notesapp.viewModel

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import com.example.notesapp.dataclass.Note
import com.example.notesapp.provider.NotesProvider

class NotesViewModel: ViewModel() {

    var note: Note = Note(0, "", "", "#DDDDDD")

    var dbNotesList = mutableListOf<Note>()


    var noteId: Int = 0
    var noteTitle: String = ""
    var noteContent: String = ""
    var noteColor: String = ""


    var notePosition = -1
}