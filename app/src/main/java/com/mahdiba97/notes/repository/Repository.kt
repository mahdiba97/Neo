package com.mahdiba97.notes.repository

import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.db.AppDatabase
import javax.inject.Inject

class Repository @Inject constructor(private val appDatabase: AppDatabase) {

  suspend fun addNote(note: NoteEntity) = appDatabase.noteDao().insertNote(note)

  fun getLiveNotes() = appDatabase.noteDao().getLiveNotes()

  suspend fun getNoteById(noteId: Int) = appDatabase.noteDao().getNoteById(noteId)

  suspend fun deleteSelectedNotes(selectedNotes: List<NoteEntity>) =
    appDatabase.noteDao().deleteSelectedNotes(selectedNotes)

  suspend fun deleteNote(note: NoteEntity) = appDatabase.noteDao().deleteNote(note)

}