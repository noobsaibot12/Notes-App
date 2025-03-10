package com.example.notesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.data.dao.NotesDao
import com.example.notesapp.data.entity.Notes

@Database(

    entities = [ Notes::class ],
    version = 1,
    exportSchema = false

)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {

        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase( context: Context): NotesDatabase {

            return Instance ?: synchronized( this ) {

                Room.databaseBuilder( context , NotesDatabase::class.java , "notes_database" )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

            }

        }

    }

}