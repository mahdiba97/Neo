package com.mahdiba97.neo

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
import com.mahdiba97.neo.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var viewModel: EditorViewModel
    private lateinit var binding: EditorFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)
        binding = EditorFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        binding.noteEditor.setText(args.id.toString())

        // When user press back button saveAndReturn method will revoke
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            })

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun saveAndReturn(): Boolean {
        findNavController().navigateUp()
        return true
    }

}