package com.example.mytodoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mytodoapp.database.NoteDatabase
import com.example.mytodoapp.repo.NoteRepository
import com.example.mytodoapp.viewmodel.NoteViewModel
import com.example.mytodoapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }
    private fun setupViewModel(){
         val noteRepository=NoteRepository(NoteDatabase.getInstance(this))
        val viewModelProviderFactory=ViewModelFactory(application,noteRepository)
        noteViewModel=ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }
}