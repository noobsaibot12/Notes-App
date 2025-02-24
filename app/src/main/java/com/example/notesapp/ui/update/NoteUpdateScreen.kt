package com.example.notesapp.ui.update

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.NotesTopAppBar
import com.example.notesapp.R
//import com.example.notesapp.ui.AppViewModelProvider
import com.example.notesapp.ui.navigation.NavigationDestination
import com.example.notesapp.ui.noteEntry.NoteEntryBody
import org.koin.androidx.compose.koinViewModel

object NoteUpdateDestination: NavigationDestination {

    override val route: String = "UPDATE_NOTE"
    override val titleRes: Int = R.string.update
    const val arg = "noteId"
    val routeWithArgs = "$route/{$arg}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteUpdateScreen (

    modifier: Modifier = Modifier,
    navigateBack: () -> Boolean,
    onNavigateUp: () -> Boolean,
    viewModel: NoteUpdateViewModel = koinViewModel(),
    id: Int

) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold (

        modifier = modifier.nestedScroll( scrollBehavior.nestedScrollConnection ),
        topBar = {

            NotesTopAppBar(

                title = stringResource( id = NoteUpdateDestination.titleRes ),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior

            )

        }

    ) { innerPadding ->

        NoteEntryBody(

            noteUiState = viewModel.noteUpdateUiState.value,
            onValueChange = viewModel::updateNoteUpdateUiState,
            onSaveClick = {

                viewModel.updateNote()
                navigateBack()

            },
            modifier = Modifier
                .padding(

                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()

                )
                .verticalScroll(rememberScrollState()),
            onDelete = {

                viewModel.deleteNote()
                navigateBack()

            },
            enabled = true

        )

    }


}