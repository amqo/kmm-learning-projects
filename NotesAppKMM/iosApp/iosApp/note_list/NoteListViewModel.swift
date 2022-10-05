//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Alberto Munoz on 5/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    
    @MainActor class NoteListViewModel: ObservableObject {
        
        private var notesDataSource: NotesDataSource? = nil
        private let searchNotes = SearchNotes()
        
        private var notes = [Note]()
        @Published private (set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            didSet {
                filteredNotes = searchNotes.execute(notes: notes, query: searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NotesDataSource? = nil) {
            self.notesDataSource = noteDataSource
        }
        
        func loadNotes() {
            notesDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? [Note]()
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?) {
            if (id != nil) {
                notesDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleSearchActive() {
            isSearchActive = !isSearchActive
            if (!isSearchActive) {
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource: NotesDataSource) {
            self.notesDataSource = noteDataSource
        }
    }
}
