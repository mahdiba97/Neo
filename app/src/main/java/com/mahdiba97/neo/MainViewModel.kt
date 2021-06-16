package com.mahdiba97.neo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mahdiba97.neo.data.AppDatabase
import com.mahdiba97.neo.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val appDatabase = AppDatabase.getInstance(app)

    val notesList = appDatabase?.noteDao()?.getAll()

    fun insertNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase?.noteDao()?.insertNote(noteEntity)
            }
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase?.noteDao()?.deleteNote(noteEntity)
            }
        }
    }

    fun deleteNotes(notes: List<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase?.noteDao()?.deleteNotes(notes)
            }
        }
    }

}