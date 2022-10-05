//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by Alberto Munoz on 5/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    
    private var notesDataSource: NotesDataSource
    private var noteId: Int64? = nil
    
    @StateObject var viewModel = NoteDetailViewModel()
    
    @Environment(\.presentationMode) var presentation
    
    init(notesDataSource: NotesDataSource, noteId: Int64? = nil) {
        self.notesDataSource = notesDataSource
        self.noteId = noteId
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Set a title...", text: $viewModel.noteTitle)
                .font(.title)
            TextField("Enter the content...", text: $viewModel.noteContent)
            Spacer()
        }.toolbar {
            Button(action: {
                viewModel.saveNote {
                    presentation.wrappedValue.dismiss()
                }
            }) {
                Image(systemName: "checkmark")
            }
        }
        .padding()
        .background(Color(hex: viewModel.noteColor))
        .onAppear {
            viewModel.loadNote(notesDataSource: notesDataSource, noteId: noteId)
        }
    }
}

struct NoteDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
