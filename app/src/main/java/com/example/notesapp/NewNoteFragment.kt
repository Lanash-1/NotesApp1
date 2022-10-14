package com.example.notesapp

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.notesapp.provider.NotesProvider
import com.example.notesapp.viewModel.NotesViewModel


class NewNoteFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels()

    private lateinit var noteText: EditText
    private lateinit var titleText: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(viewModel.note.color)))

        titleText = view.findViewById(R.id.titleText)
        noteText = view.findViewById(R.id.noteText)

        println(viewModel.note)

        titleText.setText(viewModel.note.title)
        noteText.setText(viewModel.note.note)
        view.setBackgroundColor(Color.parseColor(viewModel.note.color))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.done -> {
                println(viewModel.notePosition)
                if(viewModel.notePosition == 0){
                    viewModel.noteTitle = titleText.text.toString()
                    viewModel.noteContent = noteText.text.toString()
                    viewModel.noteColor = "#D4F0F0"
                    if(viewModel.noteTitle.isNotEmpty() || viewModel.noteContent.isNotEmpty()){
                        insertNote()
                    }
//                    viewModel.notesList.add(Note(0, titleText.text.toString(), noteText.text.toString(), "#DDDDDD"))
                }else{
                    viewModel.noteTitle = titleText.text.toString()
                    viewModel.noteContent = noteText.text.toString()
                    viewModel.noteColor = "#D4F0F0"
                    viewModel.noteId = viewModel.dbNotesList[viewModel.notePosition].id
                    updateNoteInDB()
                }
                moveToNotesFragment()
            }
            R.id.delete -> {
                if(viewModel.notePosition != 0){
                    deleteNoteFromDB(viewModel.dbNotesList[viewModel.notePosition].id)
                }
                moveToNotesFragment()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNoteFromDB(noteId: Int) {
        val contentResolver = (activity as AppCompatActivity).contentResolver!!
        val result = contentResolver.query(NotesProvider.CONTENT_URI, arrayOf(NotesProvider.COLUMN_ID, NotesProvider.COLUMN_TITLE, NotesProvider.COLUMN_NOTE, NotesProvider.COLUMN_COLOR), null, null, NotesProvider.COLUMN_ID)
        contentResolver.delete(NotesProvider.CONTENT_URI, "ID = ?",arrayOf(noteId.toString()))
        result?.requery()
    }

    private fun updateNoteInDB() {

        val contentResolver = (activity as AppCompatActivity).contentResolver!!
        val result = contentResolver.query(NotesProvider.CONTENT_URI, arrayOf(NotesProvider.COLUMN_ID, NotesProvider.COLUMN_TITLE, NotesProvider.COLUMN_NOTE, NotesProvider.COLUMN_COLOR), null, null, NotesProvider.COLUMN_ID)

        val cv = ContentValues()
        cv.put(NotesProvider.COLUMN_TITLE, viewModel.noteTitle)
        cv.put(NotesProvider.COLUMN_NOTE, viewModel.noteContent)
        cv.put(NotesProvider.COLUMN_COLOR, viewModel.noteColor)
        contentResolver.update(NotesProvider.CONTENT_URI, cv, "ID = ?", arrayOf(viewModel.noteId.toString()))
        result?.requery()
    }

    private fun insertNote() {
        val contentResolver = (activity as AppCompatActivity).contentResolver!!
        val result = contentResolver?.query(NotesProvider.CONTENT_URI, arrayOf(NotesProvider.COLUMN_ID, NotesProvider.COLUMN_TITLE, NotesProvider.COLUMN_NOTE, NotesProvider.COLUMN_COLOR), null, null, NotesProvider.COLUMN_ID)
        val cv = ContentValues()
        cv.put(NotesProvider.COLUMN_TITLE, viewModel.noteTitle)
        cv.put(NotesProvider.COLUMN_NOTE, viewModel.noteContent)
        cv.put(NotesProvider.COLUMN_COLOR, viewModel.noteColor)
        contentResolver.insert(NotesProvider.CONTENT_URI, cv)
        result?.requery()
    }

    private fun moveToNotesFragment(){
        parentFragmentManager.commit {
            replace(R.id.notesFragment, NotesFragment())
        }
    }

}