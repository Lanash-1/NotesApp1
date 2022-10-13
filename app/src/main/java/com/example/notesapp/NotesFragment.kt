package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapter.NotesListAdapter
import com.example.notesapp.dataclass.Note
import com.example.notesapp.interfaces.OnItemClickListener
import com.example.notesapp.viewModel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    private var adapter = NotesListAdapter()
    private val viewModel: NotesViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var notesList = listOf(
            Note(1, "Untitled note 1", "lore sdjnd sod shjdn sdichsdis sdi2 weif feirrhf sd", "#A2E1DB"),
            Note(2, "Android study materials", "android official documentation", "#FFCCB6"),
            Note(3, "Meeting agenda", "1. Welcome note 2. Report speech 3. budget planning 4. Ending note", "#F3B0C3"),
            Note(4, "How to print hello world in kotlin", "To print anything in kotlin we have to use println() and inside paranthesis withind double quotes type what you want", "#FFFFB5")
        )

        val fab = view.findViewById<FloatingActionButton>(R.id.create_note_fab)

        fab.setOnClickListener {
            viewModel.note = Note(0, "", "", "#DDDDDD")
            parentFragmentManager.commit {
                replace(R.id.notesFragment, NewNoteFragment())
                addToBackStack(null)
            }
        }

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.note = notesList[position]
                parentFragmentManager.commit {
                    replace(R.id.notesFragment, NewNoteFragment())
                    addToBackStack(null)
                }
            }
        })

        val recyclerView = view.findViewById<RecyclerView>(R.id.notes_recyclerView)

        adapter.setNotesList(notesList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
    }

}