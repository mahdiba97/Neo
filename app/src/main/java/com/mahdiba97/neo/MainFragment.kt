package com.mahdiba97.neo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.inflate(inflater, container, false)
        with(binding.mainRecycler) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
            adapter = NotesListAdapter(it, this@MainFragment)
            binding.mainRecycler.adapter = adapter
            binding.mainRecycler.layoutManager = LinearLayoutManager(activity)
        })
        return binding.root
    }

    override fun editingNote(noteId: Int) {
        Log.i("TAG__", noteId.toString())
        val action = MainFragmentDirections.actionEditorFragment(noteId)
        findNavController().navigate(action)
    }
}