package com.mahdiba97.neo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdiba97.neo.databinding.MainFragmentBinding

class MainFragment : Fragment(), NotesListAdapter.ListItemListener {
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        })
        return binding.root
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
        Log.i("TAG__", noteId.toString())
        val action = MainFragmentDirections.actionEditorFragment(noteId)
        findNavController().navigate(action)
    }

    //    Reset options menu
    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }
}