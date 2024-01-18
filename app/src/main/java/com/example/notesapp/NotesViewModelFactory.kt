package com.example.notesapp

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotesViewModelFactory(
    private val repo:Repo

) :ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass:Class<T>):T{
        return NotesViewModel(repo) as T
    }
}