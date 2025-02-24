package com.example.notesapp

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.home.HomeUiState
import com.example.notesapp.ui.navigation.NotesNavHost

@Composable
fun NotesApp () {

    val navController = rememberNavController()
    NotesNavHost( navController = navController )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotesTopAppBar (

    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    selectLayout: (Boolean) -> Unit = {},
    isLinearLayout: Boolean = true,
    toggleIcon: Int = 0,
    showIcon: Boolean = false

) {

    CenterAlignedTopAppBar(

        title = { Text( text = title ) },
        actions = {

            if ( showIcon ) {

                IconButton(

                    onClick = {

                        selectLayout(!isLinearLayout)

                    }

                ) {

                    Icon(

                        painter = painterResource(toggleIcon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground

                    )

                }

            }

        },
        navigationIcon = {

            if ( canNavigateBack ) {

                IconButton(

                    onClick = navigateUp

                ) {

                    Icon(

                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource( id = R.string.back_button)

                    )

                }

            }

        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors( containerColor = MaterialTheme.colorScheme.primaryContainer )

    )

}