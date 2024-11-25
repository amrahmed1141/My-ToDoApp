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
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.mytodoapp.MainActivity
import com.example.mytodoapp.R
import com.example.mytodoapp.databinding.FragmentAddNoteBinding
import com.example.mytodoapp.models.Note
import com.example.mytodoapp.viewmodel.NoteViewModel


class AddNoteFragment : Fragment(R.layout.fragment_add_note),MenuProvider {


    private var addNotebinding: FragmentAddNoteBinding?=null
    private val binding get() = addNotebinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onDestroy() {
        super.onDestroy()
        addNotebinding=null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       addNotebinding=FragmentAddNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel=(activity as MainActivity).noteViewModel

        noteView=view
    }
     fun saveNote(view: View){
         val noteTitle=binding.addNoteTitle.text.toString().trim()
         val noteDesc=binding.addNoteDesc.text.toString().trim()

         if (noteTitle.isNotEmpty()){
             val  note=Note(0,noteTitle,noteDesc)
             noteViewModel.addNote(note)
             Toast.makeText(context,"Note Saved Successfully",Toast.LENGTH_LONG).show()
             view.findNavController().popBackStack(R.id.homeFragment,false)
         } else{
             Toast.makeText(context,"Please Enter Note Title",Toast.LENGTH_LONG).show()
         }
     }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menu.clear()
        menuInflater.inflate(R.menu.add_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveNote(noteView)
                true
            }
            else->false
        }
    }

}