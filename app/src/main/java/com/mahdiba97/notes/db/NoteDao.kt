package com.mahdiba97.notes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahdiba97.notes.data.NoteEntity
import io.reactivex.rxjava3.core.Maybe

@Dao
interface NoteDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: NoteEntity)

  @Query("SELECT * FROM notes ORDER BY date DESC")
  fun getLiveNotes(): LiveData<List<NoteEntity>>

  @Query("SELECT * FROM notes WHERE id = :noteId")
  suspend fun getNoteById(noteId: Int): NoteEntity?

  @Delete
  suspend fun deleteSelectedNotes(selectedNotes: List<NoteEntity>): Int

  @Delete
  suspend fun deleteNote(note: NoteEntity): Int
}