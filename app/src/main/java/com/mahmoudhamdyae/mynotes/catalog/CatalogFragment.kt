package com.mahmoudhamdyae.mynotes.catalog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.databinding.FragmentCatalogBinding

class CatalogFragment: Fragment() {

    private lateinit var binding : FragmentCatalogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this)[CatalogViewModel::class.java]
        binding.viewModel = viewModel
        binding.listNotes.adapter = NotesAdapter(NotesAdapter.OnClickListener{
            editNote(it)
        })

        binding.addNoteFab.setOnClickListener {
            addNote()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun addNote() {
        val note = Note(4, "t", "d")
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(note))
    }

    private fun editNote(note: Note) {
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(note))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.listNotes.adapter?.notifyDataSetChanged()
    }
}
