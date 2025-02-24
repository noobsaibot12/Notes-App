package com.example.notesapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.notesapp.NotesTopAppBar
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.koinViewModel

object HomeDestination: NavigationDestination {

    override val route: String = "HOME"
    override val titleRes: Int = R.string.app_name

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (

    modifier: Modifier = Modifier,
    navigateToNoteEntry: () -> Unit = {},
    navigateToNoteUpdate: ( Int ) -> Unit = {},
    viewModel: HomeViewModel = koinViewModel()

) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val isLinearLayout by viewModel.isLinearLayout.collectAsState()

    Scaffold (

        modifier = modifier.nestedScroll( scrollBehavior.nestedScrollConnection ),
        topBar = {

            NotesTopAppBar(

                title = stringResource( id = HomeDestination.titleRes ),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                selectLayout = viewModel::selectLayout,
                isLinearLayout = isLinearLayout.isLinearLayout,
                toggleIcon = homeUiState.toggleIcon,
                showIcon = true

            )

        },
        floatingActionButton = {

            FloatingActionButton(

                onClick = { navigateToNoteEntry() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding( 20.dp )

            ) {

                Icon(

                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)

                )

            }

        }

    ) { innerPadding ->

        HomeBody (

            modifier = Modifier.fillMaxSize(),
            navigateToNoteUpdate = navigateToNoteUpdate,
            contentPadding = innerPadding,
            notesList = homeUiState.noteList,
            isLinearLayout = isLinearLayout.isLinearLayout,

        )

    }

}

@Composable
fun HomeBody(

    notesList: List<Notes>,
    modifier: Modifier,
    navigateToNoteUpdate: (Int) -> Unit,
    contentPadding: PaddingValues,
    isLinearLayout: Boolean,

) {

    Column (

        modifier = modifier
            .padding( contentPadding ),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        if ( notesList.isEmpty() ) {

            Text (

                text = stringResource( id = R.string.no_notes_description ) ,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding( 20.dp )

            )

        } else {

            if ( isLinearLayout ) {

                NotesList(

                    notes = notesList,
                    onNoteClick = navigateToNoteUpdate

                )

            } else {

                NotesList_TWO_COLUMN(

                    notes = notesList ,
                    onNoteClick = navigateToNoteUpdate

                )

            }

        }

    }

}

@Composable
fun NotesList_TWO_COLUMN (

    notes: List< Notes > ,
    onNoteClick: ( Int ) -> Unit ,

) {

    LazyVerticalGrid(

        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),

    ) {

        items( notes ) {

            NoteItem(

                note = it,
                onNoteClick = onNoteClick

            )

        }

    }

}

@Composable
fun NotesList(

    notes: List<Notes>,
    onNoteClick: (Int) -> Unit,

) {

    LazyColumn (

        modifier = Modifier
            .fillMaxWidth()

    ) {

        items( notes ) {

            NoteItem(

                note = it,
                onNoteClick = onNoteClick

            )

        }

    }

}

@Composable
fun NoteItem(

    note: Notes,
    onNoteClick: (Int) -> Unit

) {

    Card(

        shape = RoundedCornerShape(10.dp),
        colors = if ( note.isSaved ) {

            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)

        } else {

            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)

        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onNoteClick(note.id) } // Add click functionality

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Align content vertically

        ) {

            Column(

                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp) // Add padding between text and image

            ) {

                Text(

                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis // Ellipsis if text overflows

                )

                Spacer(modifier = Modifier.height(4.dp)) // Add spacing between lines

                Text(

                    text = note.description,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis

                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = note.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Optional color styling

                )

            }

            if ( note.image != "" ) {
                Box(

                    modifier = Modifier
                        .size(70.dp) // Define size for the image box
                        .background(

                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(corner = CornerSize(10.dp))

                        ) // Rounded placeholder

                ) {

                    AsyncImage(

                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(note.image)
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()

                    )

                }
            }



        }

    }

}