package com.mahmoudhamdyae.mynotes

import com.google.firebase.auth.FirebaseAuth

class Utils {

    companion object {
        const val SIGN_IN_REQUEST_CODE = 1001
    }

    fun getUser() = FirebaseAuth.getInstance().currentUser
}