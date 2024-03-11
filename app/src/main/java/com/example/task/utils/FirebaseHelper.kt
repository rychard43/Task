package com.example.task.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.task.R
import com.example.task.data.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {
        private fun showToast(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun getDatabaseReference() = Firebase.database.reference
        fun getAuth() = FirebaseAuth.getInstance()
        fun getIdUser() = getAuth().currentUser?.uid ?: ""
        fun isAuthenticated() = getAuth().currentUser != null

        fun deleteTask(task: Task, fragment: Fragment) {
            getDatabaseReference()
                .child("tasks")
                .child(getIdUser())
                .child(task.id)
                .removeValue().addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        showToast(
                            fragment.getString(R.string.removeu_task),
                            fragment.requireContext()
                        )
                    } else {
                        showToast(
                            fragment.getString(R.string.erro_generico) + ": " + result.exception?.message,
                            fragment.requireContext()
                        )
                    }
                }
        }

        fun updateTask(task: Task, fragment: Fragment) {
            getDatabaseReference()
                .child("tasks")
                .child(getIdUser())
                .child(task.id)
                .setValue(task).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        showToast(
                            fragment.getString(R.string.atualizada_sucesso),
                            fragment.requireContext()
                        )
                    } else {
                        showToast(
                            fragment.getString(R.string.erro_generico) + ": " + result.exception?.message,
                            fragment.requireContext()
                        )
                    }
                }
        }

    }
}