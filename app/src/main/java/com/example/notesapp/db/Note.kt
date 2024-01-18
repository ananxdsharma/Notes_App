package com.example.notesapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="notesTable")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    val noteName:String,
    val noteContent:String
)
{
}