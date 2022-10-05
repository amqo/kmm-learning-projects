//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by Alberto Munoz on 5/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailScreen {
    
    @MainActor class NoteDetailViewModel: ObservableObject {
        
        private var notesDataSource: NotesDataSource? = nil
        
        private var noteId: Int64? = nil
        @Published var noteTitle = ""
        @Published var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateRandomColor()
        
    
        init(notesDataSource: NotesDataSource? = nil) {
            self.notesDataSource = notesDataSource
        }
        
        private func loadNoteIfExists(id: Int64?) {
            if id != nil {
                self.noteId = id
                notesDataSource?.getNoteById(id: id!, completionHandler: { note, error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().generateRandomColor()
                })
            }
        }
        
        func saveNote(onSaved: @escaping () -> Void) {
            notesDataSource?.insertNote(
                note: Note(
                    id: noteId == nil ? nil : KotlinLong(longLong: self.noteId!),
                    title: self.noteTitle,
                    content: self.noteContent,
                    colorHex: self.noteColor,
                    created: DateTimeUtil().now()
                ), completionHandler: { error in
                    onSaved()
                })
        }
        
        func loadNote(notesDataSource: NotesDataSource, noteId: Int64?) {
            self.notesDataSource = notesDataSource
            loadNoteIfExists(id: noteId)
        }
    }
}
