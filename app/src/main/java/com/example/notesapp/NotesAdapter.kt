package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.db.Note

class NotesAdapter(
    private val listOfNotes:List<Note>,
    private val clickListener:ClickListener
):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    class NotesViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val textName: TextView =itemView.findViewById(R.id.text_note_name)
        val textContent:TextView=itemView.findViewById(R.id.text_note_content)
        val imgDelete: ImageView =itemView.findViewById(R.id.img_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_note,parent,false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfNotes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote=listOfNotes[position]
        holder.textName.text=currentNote.noteName
        holder.textContent.text=currentNote.noteContent
        holder.itemView.setOnClickListener{
            clickListener.updateNote(currentNote)
        }
        holder.imgDelete.setOnClickListener{
            clickListener.delete(currentNote)
        }

    }
    interface ClickListener{
        fun updateNote(note:Note)
        fun delete(note:Note)
    }
}