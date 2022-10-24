package com.mahmoudhamdyae.mynotes.catalog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.mahmoudhamdyae.mynotes.R
import com.mahmoudhamdyae.mynotes.Utils
import com.mahmoudhamdyae.mynotes.databinding.FragmentCatalogBinding

@Suppress("DEPRECATION")
class CatalogFragment : Fragment() {

    private lateinit var binding: FragmentCatalogBinding

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
            editNote(it.id)
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
        findNavController().navigate(CatalogFragmentDirections.actionCatalogFragmentToEditorFragment())
    }

    private fun editNote(noteId: Long) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToEditorFragment(
                noteId
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
        // Give users the option to sign in / register with their email or Google account.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            Utils.SIGN_IN_REQUEST_CODE
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Log.d(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}")
            } else {
                // Sign in failed.
                Log.d(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    companion object {
        private const val TAG = "CatalogFragment"
    }
}
