package com.mahmoudhamdyae.mynotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        private var RC_SIGN_IN = 1
//        // Initialize Firebase Auth
//        auth = Firebase.auth
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            setAuth();
//        }
//    }
//
//    private fun setAuth() {
//        // Choose authentication providers
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.PhoneBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build())
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//            AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers as MutableList<AuthUI.IdpConfig>)
//                .setIsSmartLockEnabled(false)
//                .setLogo(R.mipmap.ic_launcher)
//                .build(),
//            RC_SIGN_IN)
//    }
}