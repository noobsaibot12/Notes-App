package com.example.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notesapp.ui.home.HomeDestination
import com.example.notesapp.ui.home.HomeScreen
import com.example.notesapp.ui.noteEntry.NoteEntryDestination
import com.example.notesapp.ui.noteEntry.NoteEntryScreen
import com.example.notesapp.ui.update.NoteUpdateDestination
import com.example.notesapp.ui.update.NoteUpdateScreen

@Composable
fun NotesNavHost (

    navController: NavHostController,
    modifier: Modifier = Modifier

) {

//    val viewModel: HomeViewModel = viewModel( factory = AppViewModelProvider.Factory )

    NavHost(

        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier

    ) {

        composable(

            route = HomeDestination.route

        ) {

            HomeScreen(

                navigateToNoteEntry = {

                    navController.navigate( route = NoteEntryDestination.route )

                },
                navigateToNoteUpdate = { note ->

                    navController.navigate( route = "${NoteUpdateDestination.route}/${note}" )

                },

            )

        }

        composable(

            route = NoteEntryDestination.route

        ) {

            NoteEntryScreen(

                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },

            )

        }

        composable(

            route = NoteUpdateDestination.routeWithArgs,
            arguments = listOf( navArgument( NoteUpdateDestination.arg ) {

                type = NavType.IntType

            } )

        ) {

            NoteUpdateScreen(

                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                id = it.arguments?.getInt( NoteUpdateDestination.arg ) ?: 0,

            )

        }

    }

}