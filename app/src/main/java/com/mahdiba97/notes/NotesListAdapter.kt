package com.mahdiba97.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.databinding.ListItemBinding
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NotesListAdapter(
  val noteId: (Int) -> Unit, val numberOfSelectedItems: (Int) -> Unit
) :
  RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {
  internal var notesItem = BehaviorSubject.create<List<NoteEntity>>()
  var selectedNotes = arrayListOf<NoteEntity>()

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ListItemBinding.bind(itemView)

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
    )
  }

  override fun getItemCount() = notesItem.blockingFirst().size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val note = notesItem.blockingFirst()[position]
    with(holder.binding) {
      noteText.text = note.text

      root.setOnClickListener {
        if (selectedNotes.size == 0) {
          noteId(note.id)
        }
      }


      fab.setOnClickListener {
        if (selectedNotes.contains(note)) {
          selectedNotes.remove(note)
          fab.setImageResource(R.drawable.ic_note)
        } else {
          selectedNotes.add(note)
          fab.setImageResource(R.drawable.ic_check)
        }
        numberOfSelectedItems(selectedNotes.size)
      }
      fab.setImageResource(
        if (selectedNotes.contains(note)) R.drawable.ic_check
        else R.drawable.ic_note
      )
    }
  }
}