package com.mahdiba97.notes.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdiba97.notes.CURSOR_POSITION_KEY
import com.mahdiba97.notes.NOTE_TEXT_KEY
import com.mahdiba97.notes.R
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.databinding.EditorFragmentBinding
import java.util.*

class EditorFragment : Fragment() {
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var viewModel: EditorViewModel
    private lateinit var binding: EditorFragmentBinding
    private var noteId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        noteId = args.id
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
            if (noteId == 0) {
                it.title = getString(R.string.new_note)
            } else {
                it.title = getString(R.string.edit_note)
                viewModel.getNoteById(noteId)
            }
        }
        setHasOptionsMenu(true)

        // When user press back button saveAndReturn method will revoke
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            })
        viewModel.currentNote.observe(viewLifecycleOwner, {
            val savedString = savedInstanceState?.getString(NOTE_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            binding.noteEditor.setText(savedString ?: it.text)
            binding.noteEditor.setSelection(cursorPosition)
        })
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.noteEditor)
        {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun saveAndReturn(): Boolean {
        val text = binding.noteEditor.text.trim().toString()
        val noteEntity = NoteEntity(noteId, Date(), text)
        if (binding.noteEditor.text.isNotEmpty()) {
            viewModel.insertNote(NoteEntity(noteId, Date(), text))
        } else viewModel.deleteNote(noteEntity)
        findNavController().navigateUp()
        return true
    }

}