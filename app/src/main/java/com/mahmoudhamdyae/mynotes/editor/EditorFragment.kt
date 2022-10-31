package com.mahmoudhamdyae.mynotes.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.mynotes.R
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.databinding.FragmentEditorBinding


@Suppress("DEPRECATION")
class EditorFragment : Fragment() {

    private var note: Note = Note()
    private lateinit var viewModel: EditorViewModel
    private lateinit var binding: FragmentEditorBinding

    private var isNew: Boolean = false
    private var mNoteHasChanged = false

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mNoteHasChanged boolean to true.
     */
    @SuppressLint("ClickableViewAccessibility")
    private var mTouchListener: View.OnTouchListener? =
        View.OnTouchListener { _, _ ->
            mNoteHasChanged = true
            false
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorBinding.inflate(inflater)
        binding.lifecycleOwner = this

        note = EditorFragmentArgs.fromBundle(requireArguments()).note

        val viewModelFactory = EditorViewModelFactory(note, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditorViewModel::class.java]
        binding.viewModel = viewModel

        if (note.id == "") {
            isNew = true
        } else {
            viewModel.selectedNote.observe(viewLifecycleOwner) {
                note = it
            }
        }

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        binding.titleEditText.setOnTouchListener(mTouchListener)
        binding.descriptionEditText.setOnTouchListener(mTouchListener)

        binding.titleEditText.requestFocus()

        binding.saveNoteFab.setOnClickListener {
            saveOrUpdateNote()
        }

        viewModel.isFinished.observe(viewLifecycleOwner) {
            if (it) {
                navigateUp()
                viewModel.closeFragment()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        setHasOptionsMenu(true)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    saveOrUpdateNote()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    private fun saveOrUpdateNote() {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        if (title.isBlank() && description.isBlank())
            navigateUp()
        else if (isNew)
            saveNote(Note(title = title, description = description))
        else if (mNoteHasChanged)
            updateNote(Note(note.id, title, description))
        else
            navigateUp()
    }

    private fun saveNote(note: Note) {
        viewModel.saveNote(note)
    }

    private fun updateNote(note: Note) {
        viewModel.updateNote(note)
    }

    private fun delNote(noteId: String) {
        viewModel.delNote(noteId)
        Toast.makeText(context, getString(R.string.toast_note_deleted), Toast.LENGTH_SHORT).show()
    }

    private fun showDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(getString(R.string.dialog_delete_msg))
                setPositiveButton(getString(R.string.dialog_yes)
                ) { _, _ ->
                    // User clicked Yes button
                    // Delete Note
                    delNote(note.id)
                }
                setNegativeButton(getString(R.string.dialog_no)
                ) { _, _ ->
                    // User clicked Cancel button
                    // Do nothing
                }
            }

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    private fun navigateUp() {
        findNavController().navigateUp()
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
                showDialog()
                true
            }
//            android.R.id.home -> {
//                saveOrUpdateNote()
//                navigateUp()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isNew)
            // Set title New Note
            activity?.title = getString(R.string.title_editor_new_note)
        else
            // Set title Edit Note
            activity?.title = getString(R.string.title_editor_edit_note)
    }

    @Deprecated("Deprecated in Java")
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        // If this is a new Note, hide the "Delete" menu item.
        if (isNew)
            menu.findItem(R.id.action_delete).isVisible = false
    }
}