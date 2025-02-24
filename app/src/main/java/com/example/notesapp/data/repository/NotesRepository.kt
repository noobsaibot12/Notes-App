package com.example.notesapp.data.repository

import com.example.notesapp.data.entity.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotesStream(): Flow<List<Notes>>

    fun getNoteStream(id: Int): Flow<Notes>

    suspend fun deleteCompletedNote(id: Int)

    suspend fun addNote(note: Notes)

    suspend fun updateNote(note: Notes)

}