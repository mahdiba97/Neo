package com.mahdiba97.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.databinding.MainFragmentBinding

class MainFragment : Fragment(), NotesListAdapter.ListItemListener {
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.title = getString(R.string.app_name)
        }
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.inflate(inflater, container, false)
        with(binding.mainRecycler) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }
        viewModel.notesList?.observe(viewLifecycleOwner, {
            adapter = NotesListAdapter(it, this@MainFragment)
            binding.mainRecycler.adapter = adapter
            binding.mainRecycler.layoutManager = LinearLayoutManager(activity)
            val selectedNote =
                savedInstanceState?.getParcelableArrayList<NoteEntity>(SELECTED_NOTE_KEY)
                    ?: emptyList<NoteEntity>()
            adapter.selectedNotes.addAll(selectedNote)

        })
        binding.fabMain.setOnClickListener {
            editingNote(NEW_NOTE_ID)
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(SELECTED_NOTE_KEY, adapter.selectedNotes)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId = if (
            this::adapter.isInitialized && adapter.selectedNotes.isNotEmpty())
            R.menu.delete_menu_main
        else
            R.menu.menu_main

        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> deleteNotes()
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun deleteNotes(): Boolean {
        viewModel.deleteNotes(adapter.selectedNotes)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedNotes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)
        return true
    }

    override fun editingNote(noteId: Int) {
        val action = MainFragmentDirections.actionEditorFragment(noteId)
        findNavController().navigate(action)
    }

    //    Reset options menu
    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }
}