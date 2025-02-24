package com.example.notesapp.ui.noteEntry

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.NotesTopAppBar
import com.example.notesapp.R
//import com.example.notesapp.ui.AppViewModelProvider
import com.example.notesapp.ui.navigation.NavigationDestination
import com.example.notesapp.ui.utils.saveImageToInternalStorage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Currency
import java.util.Locale


object NoteEntryDestination: NavigationDestination {

    override val route: String = "ADD_NOTE"
    override val titleRes: Int = R.string.add_note

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen (

    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = koinViewModel()

) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val noteUiState = viewModel.noteUiState

    Scaffold (

        modifier = modifier.nestedScroll( scrollBehavior.nestedScrollConnection ),
        topBar = {

            NotesTopAppBar(

                title = stringResource( id = NoteEntryDestination.titleRes ),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateUp

            )

        }

    ) { innerPadding ->

        NoteEntryBody(

            noteUiState = viewModel.noteUiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = {

                coroutineScope.launch {

                    viewModel.saveNote()
                    navigateBack()

                }

            },
            modifier = Modifier
                .padding(

                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()

                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),


        )

    }

}

@Composable
fun NoteEntryBody(

    noteUiState: NoteUiState,
    onValueChange: (NoteDetail) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier,
    onDelete: () -> Unit = {},
    enabled: Boolean = false

) {

    Column(

        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        NoteInputForm(

            noteDetail = noteUiState.noteDetail,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()

        )

        Row(

            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {

            Box ( modifier = Modifier.weight( 1f ), contentAlignment = Alignment.Center ) {

                Row ( verticalAlignment = Alignment.CenterVertically ) {

                    Text(text = "Save" , style = MaterialTheme.typography.bodyMedium , fontWeight = FontWeight.Bold)

                    Checkbox(

                        checked = noteUiState.noteDetail.isSaved,
                        onCheckedChange = { onValueChange(noteUiState.noteDetail.copy(isSaved = !noteUiState.noteDetail.isSaved)) }

                    )
                }
            }

            if ( enabled ) {

                Box(modifier = Modifier, contentAlignment = Alignment.Center) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(

                            text = "Completed",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold

                        )

                        Checkbox(

                            checked = noteUiState.noteDetail.isCompleted,
                            onCheckedChange = {

                                onValueChange(

                                    noteUiState.noteDetail.copy(

                                        isCompleted = !noteUiState.noteDetail.isCompleted

                                    )

                                )

                            }

                        )
                    }
                }

            }

            if ( enabled ) {

                Button(

                    onClick = onDelete,
                    enabled = noteUiState.noteDetail.isCompleted,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier // Adjust weight to share equally

                ) {

                    Text(text = stringResource(R.string.delete))

                }
            }

        }

        Button(

            onClick = onSaveClick,
            enabled = noteUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()// Adjust weight to share equally

        ) {

            Text(text = stringResource(R.string.save_action))

        }

    }

}


@Composable
fun NoteInputForm(

    noteDetail: NoteDetail,
    modifier: Modifier = Modifier,
    onValueChange: (NoteDetail) -> Unit,
    enabled: Boolean = true

) {

    val localContext = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(

        contract = ActivityResultContracts.GetContent()

    ) { uri ->

        uri?.let {

            val context = localContext
            val savedImagePath = saveImageToInternalStorage(context, it)
            onValueChange(noteDetail.copy(image = savedImagePath))

        }

    }


    Column(

        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))

    ) {

        OutlinedTextField(

            value = noteDetail.title,
            onValueChange = { onValueChange(noteDetail.copy(title = it)) },
            label = { Text(stringResource(R.string.note_title_required)) },
            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer

            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true

        )

        OutlinedTextField(

            value = noteDetail.description,
            onValueChange = { onValueChange(noteDetail.copy(description = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(stringResource(R.string.note_description_required)) },
            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer

            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true

        )

        Button(

            onClick = { pickImage.launch("image/*") }, // Launch the image picker
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()

        ) {

            Text(text = stringResource(id = R.string.add_image))

        }

        if (enabled) {

            Text(

                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))

            )

        }

    }

}
