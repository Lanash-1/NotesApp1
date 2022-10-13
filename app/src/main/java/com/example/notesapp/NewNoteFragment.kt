package com.example.notesapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.notesapp.viewModel.NotesViewModel


class NewNoteFragment : Fragment() {

    private val viewModel: NotesViewModel by activityViewModels()

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

        val editText = view.findViewById<EditText>(R.id.titleText)
        val noteText = view.findViewById<EditText>(R.id.noteText)

        println(viewModel.note)

        editText.setText(viewModel.note.title)
        noteText.setText(viewModel.note.note)
        view.setBackgroundColor(Color.parseColor(viewModel.note.color))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
    }

}