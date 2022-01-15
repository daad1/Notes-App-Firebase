package com.example.notesappfull

import androidx.lifecycle.LiveData

class Repository(private val dao:NoteDao) {
    suspend fun addNote(note: Note){
        dao.addNote(note)
    }

    val getAllNote: LiveData<List<Note>> = dao.getAllNote()

    suspend fun updateNote(note: Note){
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        dao.deleteNote(note)
    }
}