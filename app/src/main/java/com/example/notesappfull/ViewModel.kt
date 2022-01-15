package com.example.notesappfull

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel (application: Application): AndroidViewModel(application) {
    private val repository:Repository
    val getAllNote: LiveData<List<Note>>
    init{
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = Repository(dao)
        getAllNote = repository.getAllNote
    }

    fun addNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch{ repository.addNote(note) }
    }

    fun updateNote(note: Note)
    { CoroutineScope(Dispatchers.IO).launch { repository.updateNote(note) }
    }

    fun deleteNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch { repository.deleteNote(note) }
    }
}