package com.mahdiba97.notes.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
  fun getNoteById(noteId: Int, note: (NoteEntity?) -> Unit) {
    viewModelScope.launch {
      note(repository.getNoteById(noteId))
    }
  }


  fun addNote(note: NoteEntity) {
    viewModelScope.launch {
      repository.addNote(note)
    }
  }

  fun deleteNote(note: NoteEntity) {
    viewModelScope.launch {
      repository.deleteNote(note)
    }
  }

  fun getViewModel(it: ViewModelStoreOwner) =
    ViewModelProvider(it).get(EditorViewModel::class.java)
}