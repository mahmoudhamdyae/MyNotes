package com.mahmoudhamdyae.mynotes.editor

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.mynotes.R
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.databinding.FragmentEditorBinding

@Suppress("DEPRECATION")
class EditorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditorBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val note = EditorFragmentArgs.fromBundle(requireArguments()).note
        val viewModelFactory = EditorViewModelFactory(note, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[EditorViewModel::class.java]
        binding.viewModel = viewModel

        binding.saveNoteFab.setOnClickListener {
            saveOrUpdateNote(
                viewModel, Note(
                    title = binding.titleEditText.text.toString(),
                    description = binding.descriptionEditText.text.toString()
                )
            )
        }

        viewModel.isFinished.observe(viewLifecycleOwner) {
            if (it)
                findNavController().navigateUp()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "inflater.inflate(R.menu.menu_editor, menu)",
            "com.mahmoudhamdyae.mynotes.R"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_editor, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        Toast.makeText(context, "Delete Note", Toast.LENGTH_SHORT).show()
    }

    private fun saveOrUpdateNote(viewModel: EditorViewModel, note: Note) {
        viewModel.saveNote(note)
        viewModel.closeFragment()
    }
}