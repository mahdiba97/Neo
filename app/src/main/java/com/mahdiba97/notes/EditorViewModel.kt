package com.mahdiba97.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.AppDatabase
import com.mahdiba97.notes.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app: Application) : AndroidViewModel(app) {
    val database = AppDatabase.getInstance(app)
    val currentNote = MutableLiveData<NoteEntity>()
    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val noteEntity = database?.noteDao()?.getNote(noteId)
                currentNote.postValue(noteEntity!!)
            }
        }
    }

    fun insertNote(note: NoteEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.insertNote(note)
            }
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteNote(note)
            }
        }
    }

}