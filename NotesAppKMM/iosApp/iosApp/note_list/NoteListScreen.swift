//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Alberto Munoz on 5/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    
    private var notesDataSource: NotesDataSource
    @StateObject var viewModel = NoteListViewModel()
    
    @State private var isNoteSelected = false
    @State private var selectedNoteId: Int64? = nil
    
    init(notesDataSource: NotesDataSource) {
        self.notesDataSource = notesDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                NavigationLink(
                    destination: NoteDetailScreen(
                        notesDataSource: notesDataSource,
                        noteId: selectedNoteId
                    ),
                    isActive: $isNoteSelected
                ) {
                    EmptyView()
                }.hidden()
                
                HideableSearchTextField<NoteDetailScreen>(
                    onSearchToggled: {
                        viewModel.toggleSearchActive()
                    },
                    destinationProvider: {
                        NoteDetailScreen(
                            notesDataSource: notesDataSource
                        )
                    },
                    isSearchActive: viewModel.isSearchActive,
                    searchText: $viewModel.searchText
                )
                .frame(maxWidth: .infinity, minHeight: 40)
                .padding()
                
                if !viewModel.isSearchActive {
                    Text("All Notes")
                        .font(.title2)
                }
            }
            
            List {
                ForEach(viewModel.filteredNotes, id: \.self.id) { note in
                    Button(action: {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }) {
                        NoteItem(note: note, onDeleteClick: {
                            viewModel.deleteNoteById(id: note.id?.int64Value)
                        })
                    }
                }
            }
            .onAppear {
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }
        .onAppear {
            viewModel.setNoteDataSource(noteDataSource: notesDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
