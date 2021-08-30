package com.mahdiba97.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.AppDatabase
import com.mahdiba97.notes.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel(app: Application) : AndroidViewModel(app) {
    private val database = AppDatabase.getInstance(app)
    private val _currentNote = MutableLiveData<NoteEntity>()
    val currentNote: LiveData<NoteEntity> = _currentNote
    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val noteEntity = database?.noteDao()?.getNote(noteId)
                _currentNote.postValue(noteEntity!!)
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