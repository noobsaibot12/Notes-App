package com.example.notesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(

    notesRepository: NotesRepository,
    private val userPreferencesRepository: UserPreferencesRepository

) : ViewModel() {

    val homeUiState: StateFlow< HomeUiState > =
        notesRepository
            .getNotesStream()
            .map { newVal ->

                HomeUiState( noteList = newVal )

            }
            .stateIn(

                scope = viewModelScope ,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS) ,
                initialValue = HomeUiState()

            )

    val isLinearLayout: StateFlow< HomeUiState > = userPreferencesRepository.isLinearLayout
        .map { isLinearLayout ->

            HomeUiState( isLinearLayout = isLinearLayout )

        }
        .stateIn(

            scope = viewModelScope ,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS) ,
            initialValue = HomeUiState()

        )

    fun selectLayout( isLinearLayout: Boolean ) {

        viewModelScope.launch {

            userPreferencesRepository.saveLayoutPreference( isLinearLayout = isLinearLayout )

        }

    }

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

    }

}

data class HomeUiState (

    val noteList: List< Notes > = listOf(),
    val isLinearLayout: Boolean = true,
    val toggleIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout

)