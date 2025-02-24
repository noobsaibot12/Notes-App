package com.example.notesapp.ui.noteEntry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.repository.NotesRepository
import java.text.SimpleDateFormat
import java.util.Locale

class NoteEntryViewModel(

    private val notesRepository: NotesRepository

) : ViewModel() {

    var noteUiState: NoteUiState by mutableStateOf( NoteUiState() )
        private set

    fun updateUiState( noteDetail: NoteDetail ) {

        noteUiState = NoteUiState(

            noteDetail = noteDetail,
            isEntryValid = validateInput( noteDetail )

        )

    }

    private fun validateInput( uiState: NoteDetail = noteUiState.noteDetail ): Boolean {

        return with( uiState ) {

            title.isNotBlank() && description.isNotBlank()

        }

    }

    suspend fun saveNote() {

        if ( validateInput() ) {

            notesRepository.addNote( noteUiState.noteDetail.toNotes() )

        }

    }

}

data class NoteUiState(

    val noteDetail: NoteDetail = NoteDetail(),
    val isEntryValid: Boolean = false

)

data class NoteDetail(

    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val timestamp: String = getCurrentTimestamp(),
    val image: String = "",
    val isSaved: Boolean = false,
    val isCompleted: Boolean = false

)

fun NoteDetail.toNotes(): Notes = Notes(

    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    image = image,
    isSaved = isSaved,
    isCompleted = isCompleted

)

fun Notes.toNoteUiState( isEntryValid: Boolean ): NoteUiState = NoteUiState(

    noteDetail = this.toNoteDetail(),
    isEntryValid = isEntryValid

)

fun Notes.toNoteDetail(): NoteDetail = NoteDetail(

    id = id,
    title = title,
    description = description,
    timestamp = timestamp,
    image = image,
    isSaved = isSaved,
    isCompleted = isCompleted

)

fun getCurrentTimestamp(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(System.currentTimeMillis())
}