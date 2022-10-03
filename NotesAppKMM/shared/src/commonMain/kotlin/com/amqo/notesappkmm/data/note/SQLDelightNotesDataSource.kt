package com.amqo.notesappkmm.data.note

import com.amqo.notesappkmm.database.NotesDatabase
import com.amqo.notesappkmm.domain.note.Note
import com.amqo.notesappkmm.domain.note.NotesDataSource
import com.amqo.notesappkmm.domain.time.DateTimeUtil

class SQLDelightNotesDataSource(
    db: NotesDatabase
): NotesDataSource {

    private val queries = db.notesQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}