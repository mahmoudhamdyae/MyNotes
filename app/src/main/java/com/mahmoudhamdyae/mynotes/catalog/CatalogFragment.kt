package com.mahmoudhamdyae.mynotes.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.mynotes.R
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
            editNote(it.id)
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
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment())
    }

    private fun editNote(noteId: Long) {
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(noteId))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set title My Notes
        activity?.title = getString(R.string.app_name)
    }
}
