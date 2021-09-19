package com.mahdiba97.notes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.databinding.MainFragmentBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class MainFragment : Fragment() {
  private lateinit var binding: MainFragmentBinding
  private lateinit var viewModel: MainViewModel
  private lateinit var adapter: NotesListAdapter
  private val bag = CompositeDisposable()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    actionbar(activity)?.let {
      it.setDisplayHomeAsUpEnabled(false)
      it.title = getString(R.string.app_name)
    }
    setHasOptionsMenu(true)
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    binding = MainFragmentBinding.inflate(inflater, container, false)

    binding.fabMain.setOnClickListener {
      navigateToEditorFragment(NEW_NOTE_ID)
    }
    notesListSetup(savedInstanceState)
    onBackPressed()
    return binding.root
  }

  private fun notesListSetup(savedInstanceState: Bundle?) {
    adapter = NotesListAdapter({ //onClick
      navigateToEditorFragment(it)
    }, {//number on selected item, it's for change toolbar title to recursive number
      changeTitleAndArrowDirection(it)
    })

    adapter.notesItem.subscribeOn(AndroidSchedulers.mainThread()).subscribe {
      for (i in adapter.positions)
        adapter.notifyItemChanged(i)
    }.addTo(bag)
    with(binding.mainRecycler) {
      setHasFixedSize(true)
      val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
      addItemDecoration(divider)
    }
    viewModel.notesList?.observe(viewLifecycleOwner, { notes ->
      adapter.notesItem.onNext(notes)
      binding.mainRecycler.adapter = adapter
      binding.mainRecycler.layoutManager = LinearLayoutManager(activity)

      val selectedNote = savedInstanceState?.getParcelableArrayList(SELECTED_NOTE_KEY)
        ?: emptyList<NoteEntity>() //save state of the selected items when orientation change
      adapter.selectedNotes.addAll(selectedNote)
    })
  }


  private fun onBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback(
      viewLifecycleOwner,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          if (adapter.selectedNotes.isEmpty()) remove()
          else handleBackPressedEvent()
        }
      })
  }

  override fun onSaveInstanceState(outState: Bundle) {
    if (this::adapter.isInitialized) {
      outState.putParcelableArrayList(SELECTED_NOTE_KEY, adapter.selectedNotes)
    }
    super.onSaveInstanceState(outState)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    val menuId: Int
    if (
      this::adapter.isInitialized && adapter.selectedNotes.isNotEmpty()) {
      menuId = R.menu.delete_menu_main
      actionbar(activity)?.setDisplayHomeAsUpEnabled(true)
    } else {
      actionbar(activity)?.setDisplayHomeAsUpEnabled(false)
      menuId = R.menu.menu_main
    }
    inflater.inflate(menuId, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        handleBackPressedEvent()
        true
      }
      R.id.action_delete -> deleteNotes()
      R.id.action_share -> {
        val stringBuilder = StringBuilder()
        for (i in adapter.selectedNotes) {
          stringBuilder.append("$i \n")
        }
        val intent = Intent().apply {
          action = Intent.ACTION_SEND
          type = "text/plain"
          putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share)))
        true
      }
      R.id.action_settings -> {
        startActivity(Intent(context, SettingsActivity::class.java))
        true
      }
      R.id.action_about -> {
        val action = MainFragmentDirections.actionAboutFragment()
        findNavController().navigate(action)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }

  }

  private fun handleBackPressedEvent() {
    val positions = adapter.positions
    for (i in positions) {
      adapter.notifyItemChanged(i)
    }
    adapter.selectedNotes.clear()
    adapter.positions.clear()
    actionbar(activity)?.setDisplayHomeAsUpEnabled(false)
    actionbar(activity)?.title = getString(R.string.app_name)
    requireActivity().invalidateOptionsMenu()
  }

  private fun deleteNotes(): Boolean {
    viewModel.deleteNotes(adapter.selectedNotes)
    Handler(Looper.getMainLooper()).postDelayed({
      adapter.selectedNotes.clear()
      adapter.positions.clear()
      changeTitleAndArrowDirection(0)
    }, 100)
    return true
  }

  private fun navigateToEditorFragment(noteId: Int) {
    val action = MainFragmentDirections.actionEditorFragment(noteId)
    findNavController().navigate(action)
  }


  private fun changeTitleAndArrowDirection(count: Int) {
    val arrowDirection = when (PrefHelper.getLanguage(requireContext())) {
      "Persian" -> R.drawable.ic_arrow_forward
      else -> R.drawable.ic_arrow_back
    }
    actionbar(activity)?.setHomeAsUpIndicator(arrowDirection)
    if (count != 0) {
      actionbar(activity)?.title = count.toString()
    } else
      actionbar(activity)?.title = getString(R.string.app_name)
    requireActivity().invalidateOptionsMenu()   //Reset optionsMenu
  }

  override fun onDestroy() {
    super.onDestroy()
    bag.clear()
  }

}