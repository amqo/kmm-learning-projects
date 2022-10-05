package com.amqo.notesappkmm.android.note_detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amqo.notesappkmm.android.note_detail.NodeDetailViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    navController: NavController,
    viewModel: NodeDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.noteColor))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Set a title for the Note ...",
                onValueChanged = viewModel::onNoteTitleChanged,
                isHintVisible = state.isNoteTitleHintVisible,
                onFocusChanged = { focusState ->
                    viewModel.onNoteTitleFocusedChanged(focusState.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter the content of the Note ...",
                onValueChanged = viewModel::onNoteContentChanged,
                isHintVisible = state.isNoteContentHintVisible,
                onFocusChanged = { focusState ->
                    viewModel.onNoteContentFocusedChanged(focusState.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}