package com.example.notesapp

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapter.ColorAdapter
import com.example.notesapp.interfaces.OnItemClickListener
import com.example.notesapp.provider.NotesProvider
import com.example.notesapp.viewModel.ColorViewModel
import com.example.notesapp.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class NewNoteFragment : Fragment() {

    private val notesViewModel: NotesViewModel by activityViewModels()
    private val colorViewModel: ColorViewModel by activityViewModels()

    private lateinit var noteText: EditText
    private lateinit var titleText: EditText

    private var colorAdapter = ColorAdapter()

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

        (activity as AppCompatActivity).supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(Color.parseColor(notesViewModel.note.color)))
            setDisplayHomeAsUpEnabled(true)
        }

        titleText = view.findViewById(R.id.titleText)
        noteText = view.findViewById(R.id.noteText)

        titleText.setText(notesViewModel.note.title)
        noteText.setText(notesViewModel.note.note)
        setNoteColor(notesViewModel.note.color)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                insertOrUpdateDb()
            }
            R.id.done -> {
                insertOrUpdateDb()
            }
            R.id.delete -> {
                if(notesViewModel.notePosition != -1){
                    deleteNoteFromDB(notesViewModel.dbNotesList[notesViewModel.notePosition].id)
                }
                moveToNotesFragment()
            }
            R.id.colorPicker -> {
                openColorPicker()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertOrUpdateDb() {
        if(notesViewModel.notePosition == -1){
            notesViewModel.noteTitle = titleText.text.toString()
            notesViewModel.noteContent = noteText.text.toString()
            if(notesViewModel.noteTitle.isNotEmpty() || notesViewModel.noteContent.isNotEmpty()){
                insertNote()
            }
        }else{
            notesViewModel.noteTitle = titleText.text.toString()
            notesViewModel.noteContent = noteText.text.toString()
            notesViewModel.noteColor = notesViewModel.noteColor
            notesViewModel.noteId = notesViewModel.dbNotesList[notesViewModel.notePosition].id
            updateNoteInDB()
        }
        moveToNotesFragment()
    }

    private fun openColorPicker() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.color_picker_bottom_sheet_dialog, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val colorRecyclerView = view.findViewById<RecyclerView>(R.id.colorRV)
        colorAdapter.setColorList(colorViewModel.colors)
        colorRecyclerView.adapter = colorAdapter
        colorRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        colorAdapter.setOnItemClickListener(object: OnItemClickListener {
            override fun onItemClick(position: Int) {
                setNoteColor(colorViewModel.colors[position])
            }
        })

        val selectBtn = view.findViewById<TextView>(R.id.selectColor)
        selectBtn.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun setNoteColor(color: String) {
        notesViewModel.noteColor = color
        notesViewModel.note.color = color
        val layout = requireView().findViewById<ConstraintLayout>(R.id.noteLayout)
        layout.setBackgroundColor(Color.parseColor(color))
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))
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
        cv.put(NotesProvider.COLUMN_TITLE, notesViewModel.noteTitle)
        cv.put(NotesProvider.COLUMN_NOTE, notesViewModel.noteContent)
        cv.put(NotesProvider.COLUMN_COLOR, notesViewModel.noteColor)
        contentResolver.update(NotesProvider.CONTENT_URI, cv, "ID = ?", arrayOf(notesViewModel.noteId.toString()))
        result?.requery()
    }

    private fun insertNote() {
        val contentResolver = (activity as AppCompatActivity).contentResolver!!
        val result = contentResolver?.query(NotesProvider.CONTENT_URI, arrayOf(NotesProvider.COLUMN_ID, NotesProvider.COLUMN_TITLE, NotesProvider.COLUMN_NOTE, NotesProvider.COLUMN_COLOR), null, null, NotesProvider.COLUMN_ID)
        val cv = ContentValues()
        cv.put(NotesProvider.COLUMN_TITLE, notesViewModel.noteTitle)
        cv.put(NotesProvider.COLUMN_NOTE, notesViewModel.noteContent)
        cv.put(NotesProvider.COLUMN_COLOR, notesViewModel.noteColor)
        contentResolver.insert(NotesProvider.CONTENT_URI, cv)
        result?.requery()
    }

    private fun moveToNotesFragment(){
        parentFragmentManager.commit {
            replace(R.id.notesFragment, NotesFragment())
            parentFragmentManager.popBackStack()
        }
    }

}