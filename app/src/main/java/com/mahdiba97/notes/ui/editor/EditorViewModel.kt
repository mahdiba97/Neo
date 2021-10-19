package com.mahdiba97.notes.ui.editor

import androidx.lifecycle.*
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
  private val _currentNote = MutableLiveData<NoteEntity>()
  val currentNote: LiveData<NoteEntity> = _currentNote
  fun getNoteById(noteId: Int) {
    viewModelScope.launch {
      val noteEntity = repository.getNoteById(noteId)
      _currentNote.postValue(noteEntity!!)
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