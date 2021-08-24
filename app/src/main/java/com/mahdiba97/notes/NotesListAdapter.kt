package com.mahdiba97.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.databinding.ListItemBinding

class NotesListAdapter(
    private val notesList: List<NoteEntity>,
    private val listener: ListItemListener
) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {
    var selectedNotes = arrayListOf<NoteEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        with(holder.binding) {
            noteText.text = note.text

            root.setOnClickListener {
                if (selectedNotes.size == 0) {
                    listener.editingNote(note.id)
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
                listener.onItemSelectionChanged(selectedNotes.size)
            }
            fab.setImageResource(
                if (selectedNotes.contains(note)) R.drawable.ic_check
                else R.drawable.ic_note
            )
        }


    }

    interface ListItemListener {
        fun editingNote(noteId: Int)
        fun onItemSelectionChanged(count: Int)
    }
}