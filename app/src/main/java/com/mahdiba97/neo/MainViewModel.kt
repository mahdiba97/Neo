package com.mahdiba97.neo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahdiba97.neo.data.NoteEntity
import com.mahdiba97.neo.data.SampleDataProvider

class MainViewModel : ViewModel() {
    val notesList = MutableLiveData<List<NoteEntity>>()
    init {
        notesList.value = SampleDataProvider.getNotes()
    }
}