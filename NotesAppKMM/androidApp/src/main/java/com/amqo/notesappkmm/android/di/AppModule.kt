package com.amqo.notesappkmm.android.di

import android.app.Application
import com.amqo.notesappkmm.data.local.DatabaseDriverFactory
import com.amqo.notesappkmm.data.note.SQLDelightNotesDataSource
import com.amqo.notesappkmm.database.NotesDatabase
import com.amqo.notesappkmm.domain.note.NotesDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(driver: SqlDriver): NotesDataSource {
        return SQLDelightNotesDataSource(NotesDatabase(driver))
    }
}