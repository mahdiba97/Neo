package com.mahdiba97.notes.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.AppDatabase
import com.mahdiba97.notes.data.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    private val appDatabase = AppDatabase.getInstance(app)
    val notesList = appDatabase?.noteDao()?.getAll()

    //    Room
    fun deleteNotes(notes: List<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase?.noteDao()?.deleteNotes(notes)
            }
        }
    }




}