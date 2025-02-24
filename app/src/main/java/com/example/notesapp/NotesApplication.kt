package com.example.notesapp

import android.app.Application
//import com.example.notesapp.data.appContainer.AppContainer
//import com.example.notesapp.data.appContainer.DefaultAppContainer
import com.example.notesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {

//    lateinit var container: AppContainer
//
//    override fun onCreate() {
//
//        super.onCreate()
//        container = DefaultAppContainer( this )
//
//    }

    override fun onCreate() {

        super.onCreate()

        startKoin {

            androidContext(this@NotesApplication)

            modules(

                appModule

            )

        }

    }

}