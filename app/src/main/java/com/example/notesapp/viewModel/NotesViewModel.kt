package com.example.notesapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.notesapp.dataclass.Note

class NotesViewModel: ViewModel() {

    var note: Note = Note(0, "", "", "#DDDDDD")

}