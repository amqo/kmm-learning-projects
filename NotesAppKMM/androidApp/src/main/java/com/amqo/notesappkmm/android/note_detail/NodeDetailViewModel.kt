package com.amqo.notesappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amqo.notesappkmm.domain.note.Note
import com.amqo.notesappkmm.domain.note.NotesDataSource
import com.amqo.notesappkmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NodeDetailViewModel @Inject constructor(
    private val notesDataSource: NotesDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(noteTitleField, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(isNoteTitleFocusedField, false)
    private val noteContent = savedStateHandle.getStateFlow(noteContentField, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(isNoteContentFocusedField, false)
    private val noteColor = savedStateHandle.getStateFlow(noteColorField, Note.generateRandomColor())

    val state = combine(
        noteTitle, isNoteTitleFocused, noteContent, isNoteContentFocused, noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == Note.pendingId) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                notesDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle[noteTitleField] = note.title
                    savedStateHandle[noteContentField] = note.content
                    savedStateHandle[noteColorField] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[noteTitleField] = text
    }

    fun onNoteTitleFocusedChanged(focused: Boolean) {
        savedStateHandle[isNoteTitleFocusedField] = focused
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[noteContentField] = text
    }

    fun onNoteContentFocusedChanged(focused: Boolean) {
        savedStateHandle[isNoteContentFocusedField] = focused
    }

    fun saveNote() {
        viewModelScope.launch {
            with(state.value) {
                notesDataSource.insertNote(
                    Note(
                        existingNoteId,
                        noteTitle,
                        noteContent,
                        noteColor,
                        DateTimeUtil.now()
                    )
                )
                _hasNoteBeenSaved.value = true
            }
        }
    }

    companion object {
        private const val noteTitleField = "noteTitle"
        private const val isNoteTitleFocusedField = "isNoteTitleFocused"
        private const val noteContentField = "noteContent"
        private const val isNoteContentFocusedField = "isNoteContentFocused"
        private const val noteColorField = "noteColor"
    }
}