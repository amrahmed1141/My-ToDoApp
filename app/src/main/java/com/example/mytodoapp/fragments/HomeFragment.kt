package com.example.mytodoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mytodoapp.MainActivity
import com.example.mytodoapp.R
import com.example.mytodoapp.adapters.NoteAdapter
import com.example.mytodoapp.databinding.FragmentHomeBinding
import com.example.mytodoapp.models.Note
import com.example.mytodoapp.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,MenuProvider {
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(
            this,
            viewLifecycleOwner,
            androidx.lifecycle.Lifecycle.State.RESUMED
        )
        noteViewModel = (activity as MainActivity).noteViewModel

        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)


        }
        prepareRecyclerView()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        homeBinding = null // To avoid memory leaks
    }

    fun updateUI(note: List<Note>) {
        if (note != null) {
            if (note.isNotEmpty()) {
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.emptyNotesImage.visibility = View.GONE
            } else {
                binding.homeRecyclerView.visibility = View.GONE
                binding.emptyNotesImage.visibility = View.VISIBLE

            }
        }

    }

    fun prepareRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity?.let {
            noteViewModel.getAllNote().observe(viewLifecycleOwner) { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }

    }
    fun searchNote(query:String?){
        val searchQuery = "%$query"
        noteViewModel.searchNote(searchQuery).observe(this){
            noteAdapter.differ.submitList(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchNote(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)
        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return false
    }
}