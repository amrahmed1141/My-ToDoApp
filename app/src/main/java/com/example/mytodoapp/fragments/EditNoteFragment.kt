package com.example.mytodoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodoapp.MainActivity
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentEditNoteBinding
import com.example.mytodoapp.models.Note
import com.example.mytodoapp.viewmodel.NoteViewModel
import kotlin.math.round


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {
    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private val args: EditNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding=FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        noteViewModel=(activity as MainActivity).noteViewModel
         currentNote=args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener {
            val title=binding.editNoteTitle.text.toString().trim()
            val desc=binding.editNoteDesc.text.toString().trim()
            if(title.isNotEmpty()){
                val note=Note(currentNote.id,title,desc)
                noteViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            } else{
                Toast.makeText(context,"Enter a note title please", Toast.LENGTH_LONG).show()
            }
        }




    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
      menu.clear()
        menuInflater.inflate(R.menu.edit_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            }
            else->false
        }
    }

     fun deleteNote(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("delete note")
            setMessage("you want to delete this note?")
            setPositiveButton("delete"){_,_->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }
    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }
}