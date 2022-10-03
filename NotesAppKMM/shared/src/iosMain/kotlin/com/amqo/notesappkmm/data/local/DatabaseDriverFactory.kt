package com.amqo.notesappkmm.data.local

import com.amqo.notesappkmm.database.NotesDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory{

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NotesDatabase.Schema,"notes.db")
    }
}