package com.example.mytodoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.mytodoapp.models.Note
import com.example.mytodoapp.repo.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app:Application, private val noteRepository: NoteRepository):AndroidViewModel(app) {

    fun addNote(note:Note)=
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    fun deleteNote(note:Note)=
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    fun updateNote(note:Note)=
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    fun getAllNote()=noteRepository.getAllNotes()

    fun searchNote(query: String?)=
        noteRepository.searchNote(query)
}
