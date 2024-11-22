package com.example.notesapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class MainActivity : AppCompatActivity(),NotesAdapter.ClickListener {
    private lateinit var repo:Repo
    private lateinit var notesViewModel:NotesViewModel
    private lateinit var notesViewModelFactory: NotesViewModelFactory
    private lateinit var notesDatabase:NoteDatabase
    private lateinit var notesAdapter:NotesAdapter
    private lateinit var fab:FloatingActionButton
    private lateinit var rv:RecyclerView
    private lateinit var dialog: Dialog
    private lateinit var edtNoteName:EditText
    private lateinit var edtNoteContent:EditText
    private lateinit var btnSave:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
//        notesViewModel.insert(
//            Note(
//                noteName="Some note",
//                noteContent="This is the content of note"
//            )
//        )
//        notesViewModel.insert(
//            Note(
//                noteName="some note 2",
//                noteContent="THis is content of note 2"
//            )
//        )
        notesViewModel.getAllNotes().observe(this){
            notesAdapter= NotesAdapter(it,this)
            rv.adapter=notesAdapter
            rv.layoutManager=LinearLayoutManager(this)
        }
        fab.setOnClickListener{
            openDialog(comingfromFAB = true)
        }
    }

    private fun openDialog(comingfromFAB: Boolean, enote: Note? = null) {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog)

        edtNoteName = dialog.findViewById(R.id.edt_note_name)
        edtNoteContent = dialog.findViewById(R.id.edt_note_content)
        btnSave = dialog.findViewById(R.id.btn_save)

        // If editing, populate the dialog fields with existing note data
        if (!comingfromFAB && enote != null) {
            edtNoteName.setText(enote.noteName)
            edtNoteContent.setText(enote.noteContent)
        }

        btnSave.setOnClickListener {
            val updatedNote = Note(
                id = enote?.id ?: 0, // Retain the primary key for update
                noteName = edtNoteName.text.toString(),
                noteContent = edtNoteContent.text.toString()
            )

            if (comingfromFAB) {
                notesViewModel.insert(updatedNote)
            } else {
                notesViewModel.update(updatedNote)
            }

            dialog.dismiss()
        }

        dialog.show()
    }


    private fun init() {
        notesDatabase=NoteDatabase(this)
        repo=Repo(notesDatabase.getNoteDao())
        notesViewModelFactory= NotesViewModelFactory(repo)
        notesViewModel=ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel::class.java)
        rv=findViewById(R.id.rv)
        fab=findViewById(R.id.fab)
    }

    override fun updateNote(note:Note) {
        openDialog(comingfromFAB = false, enote =note)
    }

    override fun delete(note: Note) {
        notesViewModel.delete(note)
    }
}