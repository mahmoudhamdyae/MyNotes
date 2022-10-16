package com.mahmoudhamdyae.mynotes.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.databinding.ListItemBinding

class NotesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Note, NotesAdapter.NotePropertyViewHolder>(DiffCallback) {

    class NotePropertyViewHolder(private var binding: ListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.property = note
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NotePropertyViewHolder {
        return NotePropertyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NotePropertyViewHolder, position: Int) {
        val note = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(note)
        }
        holder.bind(note)
    }

    class OnClickListener(val clickListener: (note:Note) -> Unit) {
        fun onClick(note:Note) = clickListener(note)
    }

}