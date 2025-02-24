package com.example.notesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.data.entity.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao{

    @Query( "SELECT * FROM notes ORDER BY timestamp DESC" )
    fun getNotes(): Flow< List< Notes > >

    @Query( "SELECT * FROM notes WHERE id = :id" )
    fun getNote( id: Int ): Flow< Notes >

    @Query("DELETE FROM notes WHERE id = :id AND isCompleted = 1")
    suspend fun deleteCompletedNote(id: Int)

    @Insert( onConflict = OnConflictStrategy.IGNORE )
    suspend fun addNote( note: Notes )

    @Update
    suspend fun updateNote( note: Notes )

}