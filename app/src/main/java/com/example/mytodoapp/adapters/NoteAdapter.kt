package com.example.mytodoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.databinding.NoteItemsBinding
import com.example.mytodoapp.fragments.HomeFragmentDirections
import com.example.mytodoapp.models.Note

class NoteAdapter():RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

        private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.noteTitle == newItem.noteTitle &&
                        oldItem.noteDesc == newItem.noteDesc
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
         val differ = AsyncListDiffer(this@NoteAdapter, differCallBack)
         inner class NoteViewHolder(val itemsBinding: NoteItemsBinding) :
          RecyclerView.ViewHolder(itemsBinding.root) {

    }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): NoteAdapter.NoteViewHolder {
            return NoteViewHolder(
                NoteItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
            val currentNote = differ.currentList[position]
            holder.itemsBinding.noteTitle.text=currentNote.noteTitle
            holder.itemsBinding.noteDesc.text=currentNote.noteDesc
            holder.itemView.setOnClickListener{
                val direction=HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
                    it.findNavController().navigate(direction)
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

    }
