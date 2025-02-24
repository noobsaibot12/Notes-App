package com.example.notesapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.notesapp.data.dao.NotesDao
import com.example.notesapp.data.entity.Notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class OfflineNotesRepository (

    private val notesDao: NotesDao

) : NotesRepository {

    override fun getNotesStream(): Flow<List<Notes>> = notesDao.getNotes()

    override fun getNoteStream(id: Int): Flow<Notes> = notesDao.getNote( id = id )

    override suspend fun deleteCompletedNote(id: Int) = notesDao.deleteCompletedNote( id = id )

    override suspend fun addNote(note: Notes) = notesDao.addNote( note = note )

    override suspend fun updateNote(note: Notes) = notesDao.updateNote( note = note )

}

class UserPreferencesRepository (

    private val dataStore: DataStore<Preferences>

) {

    companion object{

        val IS_LINEAR_LAYOUT = booleanPreferencesKey( "is_linear_layout" )
        const val TAG = "UserPreferencesRepo"

    }

    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch {

            if ( it is IOException) {

                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())

            } else {

                throw it

            }

        }
        .map { preferences ->

            preferences[ IS_LINEAR_LAYOUT ] ?: true

        }

    suspend fun saveLayoutPreference( isLinearLayout: Boolean ) {

        dataStore.edit { prefrence ->

            prefrence[ IS_LINEAR_LAYOUT ] = isLinearLayout

        }

    }

}