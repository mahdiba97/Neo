package com.mahdiba97.notes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
  val notesList = repository.getLiveNotes()

  fun deleteNotes(notes: List<NoteEntity>) {
    viewModelScope.launch {
      repository.deleteSelectedNotes(notes)
    }
  }


}