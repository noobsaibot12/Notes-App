package com.example.notesapp.ui
//
//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
//import androidx.lifecycle.createSavedStateHandle
//import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.notesapp.NotesApplication
//import com.example.notesapp.ui.home.HomeViewModel
//import com.example.notesapp.ui.noteEntry.NoteEntryViewModel
//import com.example.notesapp.ui.update.NoteUpdateViewModel
//
//object AppViewModelProvider{
//
//    val Factory = viewModelFactory {
//
//        initializer {
//
//            HomeViewModel(
//
//                notesRepository = notesApplication().container.notesRepository,
//                userPreferencesRepository = notesApplication().container.userPreferencesRepository
//
//            )
//
//        }
//
//        initializer {
//
//            NoteEntryViewModel(
//
//                notesRepository = notesApplication().container.notesRepository
//
//            )
//
//        }
//
//        initializer {
//
//            NoteUpdateViewModel(
//
//                notesRepository = notesApplication().container.notesRepository,
//                savaStateHandle = this.createSavedStateHandle()
//
//            )
//
//        }
//
//    }
//
//}
//
//fun CreationExtras.notesApplication(): NotesApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)