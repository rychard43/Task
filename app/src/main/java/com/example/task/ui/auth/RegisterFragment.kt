package com.example.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.ui.BaseFragment
import com.example.task.utils.FirebaseHelper
import com.example.task.utils.initToolbar
import com.example.task.utils.showBottomSheet


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonRegister.setOnClickListener {
            validateData()
        }

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
            hideKeyboard()
            binding.progressBar.isVisible = true
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.erro_registrar) + ": " + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}