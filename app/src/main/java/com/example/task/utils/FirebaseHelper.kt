package com.example.task.utils

import com.example.task.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {

        fun getDatabaseReference() = Firebase.database.reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = getAuth().currentUser?.uid ?: ""
        fun isAuthenticated() = getAuth().currentUser != null
    }
}