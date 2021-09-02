package com.mahdiba97.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mahdiba97.notes.data.NoteEntity

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: EditorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PrefHelper.setLanguage(this)

    }

    override fun onResume() {
        super.onResume()
        val received = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (received != null) {
            viewModel = ViewModelProvider(this)[EditorViewModel::class.java]
            viewModel.insertNote(NoteEntity(received))
        }
    }


}