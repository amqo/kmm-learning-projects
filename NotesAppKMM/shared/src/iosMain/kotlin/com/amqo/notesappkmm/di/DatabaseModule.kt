package com.amqo.notesappkmm.di

import com.amqo.notesappkmm.data.local.DatabaseDriverFactory
import com.amqo.notesappkmm.data.note.SQLDelightNotesDataSource
import com.amqo.notesappkmm.database.NotesDatabase
import com.amqo.notesappkmm.domain.note.NotesDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val notesDataSource: NotesDataSource by lazy {
        SQLDelightNotesDataSource(NotesDatabase(factory.createDriver()))
    }
}