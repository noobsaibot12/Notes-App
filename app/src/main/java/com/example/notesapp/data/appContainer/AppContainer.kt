package com.example.notesapp.data.appContainer

//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.preferencesDataStore
//import com.example.notesapp.data.database.NotesDatabase
//import com.example.notesapp.data.repository.NotesRepository
//import com.example.notesapp.data.repository.OfflineNotesRepository
//import com.example.notesapp.data.repository.UserPreferencesRepository
//
//interface AppContainer {
//
//    val notesRepository: NotesRepository
//    val userPreferencesRepository: UserPreferencesRepository
//
//}
//
//class DefaultAppContainer(
//
//    private val context: Context
//
//) : AppContainer {
//
//    override val notesRepository: NotesRepository by lazy {
//
//        OfflineNotesRepository( notesDao =  NotesDatabase.getDatabase( context = context ).notesDao() )
//
//    }
//
//    override val userPreferencesRepository = UserPreferencesRepository( dataStore =  context.dataStore)
//
//}
//
//private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
//
//    name = LAYOUT_PREFERENCE_NAME
//
//)