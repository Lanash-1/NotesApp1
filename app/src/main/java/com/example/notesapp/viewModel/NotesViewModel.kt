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


    var notesList = mutableListOf(
        Note(1, "Untitled note 1", "lore sdjnd sod shjdn sdichsdis sdi2 weif feirrhf sd", "#A2E1DB"),
        Note(2, "Android study materials", "android official documentation", "#FFCCB6"),
        Note(3, "Meeting agenda", "1. Welcome note 2. Report speech 3. budget planning 4. Ending note", "#F3B0C3"),
        Note(4, "How to print hello world in kotlin", "To print anything in kotlin we have to use println() and inside paranthesis withind double quotes type what you want", "#FFFFB5")
    )

    var notePosition = 0
}