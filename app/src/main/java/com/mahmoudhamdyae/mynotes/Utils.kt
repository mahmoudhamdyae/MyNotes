package com.mahmoudhamdyae.mynotes

import com.google.firebase.auth.FirebaseAuth

class Utils {
    fun getUser() = FirebaseAuth.getInstance().currentUser
}