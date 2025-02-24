package com.example.notesapp.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.notesapp.data.database.NotesDatabase
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.OfflineNotesRepository
import com.example.notesapp.data.repository.UserPreferencesRepository
import com.example.notesapp.ui.home.HomeViewModel
import com.example.notesapp.ui.noteEntry.NoteEntryViewModel
import com.example.notesapp.ui.update.NoteUpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCE_NAME)

val appModule = module {

    // Provide Database
    single { NotesDatabase.getDatabase( get() ) }

    // Provide DAO
    single { get<NotesDatabase>().notesDao() }

    // Provide Notes Repository
    single<NotesRepository> { OfflineNotesRepository(get()) }

    // Provide User Preferences Repository
    single { UserPreferencesRepository( get<Application>().dataStore ) }

    // Provide ViewModels
    viewModel { HomeViewModel(get(), get()) }

    viewModel { NoteEntryViewModel(get()) }

    viewModel { NoteUpdateViewModel(get(), get()) }

}
