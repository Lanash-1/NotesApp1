package com.example.notesapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.notesapp.dataclass.Note

class NotesViewModel: ViewModel() {

    var note: Note = Note(0, "", "", "#DDDDDD")

    var dbNotesList = mutableListOf<Note>()

    var noteId: Int = 0
    var noteTitle: String = ""
    var noteContent: String = ""
    var noteColor: String = ""


    var notePosition = -1
}