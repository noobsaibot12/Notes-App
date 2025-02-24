package com.example.notesapp.ui.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.ui.noteEntry.NoteDetail
import com.example.notesapp.ui.noteEntry.NoteUiState
import com.example.notesapp.ui.noteEntry.toNoteDetail
import com.example.notesapp.ui.noteEntry.toNoteUiState
import com.example.notesapp.ui.noteEntry.toNotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteUpdateViewModel(

    private val notesRepository: NotesRepository,
    savaStateHandle: SavedStateHandle

) : ViewModel() {

    private val noteId: Int = checkNotNull( savaStateHandle[ NoteUpdateDestination.arg ] )

    var noteUpdateUiState = mutableStateOf( NoteUiState() )
        private set

    init {

        fetchNote()

    }

    private fun fetchNote() {

        viewModelScope.launch {

            val note = notesRepository.getNoteStream( noteId ).first()

            noteUpdateUiState.value = noteUpdateUiState.value.copy(

                noteDetail = note.toNoteDetail()

            )

        }

    }

    fun updateNote() {

        viewModelScope.launch {

            val noteDetails = noteUpdateUiState.value.noteDetail
            if ( validateInput( noteDetails ) ) {

                notesRepository.updateNote( noteDetails.toNotes() )

            }

        }

    }

    fun deleteNote() {

        viewModelScope.launch {

            val noteDetails = noteUpdateUiState.value.noteDetail
            if ( validateInput( noteDetails ) ) {

                notesRepository.deleteCompletedNote( noteId )

            }

        }

    }

    fun updateNoteUpdateUiState ( note: NoteDetail ) {

        noteUpdateUiState.value = NoteUiState(

            noteDetail = note,
            isEntryValid = validateInput( note )

        )

    }

    private fun validateInput( uiState: NoteDetail ): Boolean {

        return uiState.title.isNotBlank() && uiState.description.isNotBlank()

    }

}
