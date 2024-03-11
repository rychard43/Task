package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentLoginBinding
import com.example.task.utils.FirebaseHelper
import com.example.task.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initListeners()
    }

    private fun initListeners() {
        binding.buttonCreateAccount.setOnClickListener { goToCreateAccount() }
        binding.buttonRecoverAccount.setOnClickListener { goToRecoverAccount() }
        binding.buttonLogin.setOnClickListener { goToHome() }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.erro_logar) + ": " + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun goToCreateAccount() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun goToRecoverAccount() {
        findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
    }

    private fun goToHome() {
        validateData()
    }

    private fun validateData() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        var isValid = true

        if (email.isEmpty()) {
            binding.editTextEmail.error = getString(R.string.email_obrigatorio)
            showBottomSheet(message = getString(R.string.email_obrigatorio))
            isValid = false
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = getString(R.string.senha_obrigatorio)
            showBottomSheet(message = getString(R.string.senha_obrigatorio))
            isValid = false
        }

        if (isValid) {
            binding.progressBar.isVisible = true
            login(email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}