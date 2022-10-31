package com.mahmoudhamdyae.mynotes.catalog

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.mahmoudhamdyae.mynotes.R
import com.mahmoudhamdyae.mynotes.Utils
import com.mahmoudhamdyae.mynotes.database.Note
import com.mahmoudhamdyae.mynotes.databinding.FragmentCatalogBinding

@Suppress("DEPRECATION")
class CatalogFragment : Fragment() {

    private lateinit var binding: FragmentCatalogBinding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeAuthenticationState()

        binding = FragmentCatalogBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this)[CatalogViewModel::class.java]
        binding.viewModel = viewModel
        binding.listNotes.adapter = NotesAdapter(NotesAdapter.OnClickListener {
            editNote(it)
        })

        binding.addNoteFab.setOnClickListener {
            addNote()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun addNote() {
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(Note()))
    }

    private fun editNote(note: Note) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(
                note
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set title My Notes
        activity?.title = getString(R.string.app_name)
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "inflater.inflate(R.menu.menu_catalog, menu)",
            "com.mahmoudhamdyae.mynotes.R"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_catalog, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                AuthUI.getInstance()
                    .signOut(requireContext())
                    .addOnCompleteListener {
                        launchSignInFlow()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Observes the authentication state
     */
    private fun observeAuthenticationState() {
        if (Utils().getUser() == null)
            launchSignInFlow()
    }

    private fun launchSignInFlow() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Successfully signed in
        } else {
            // Sign in failed.
            Toast.makeText(context, response?.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
