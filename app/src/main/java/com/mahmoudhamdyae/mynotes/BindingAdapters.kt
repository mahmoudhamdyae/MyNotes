package com.mahmoudhamdyae.mynotes

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.mynotes.catalog.NotesAdapter
import com.mahmoudhamdyae.mynotes.database.Note

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Note>?) {
    val adapter = recyclerView.adapter as NotesAdapter
    adapter.submitList(data)
}
