package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentLoginBinding
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.utils.initToolbar


class RegisterFragment : Fragment() {

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
            if (validateData()) {
                findNavController().navigate(R.id.action_global_homeFragment)
            }
        }

    }

    private fun validateData(): Boolean {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        var isValid = true

        if (email.isEmpty()) {
            binding.editTextEmail.error = getString(R.string.email_obrigatorio)
            Toast.makeText(context, getString(R.string.email_obrigatorio), Toast.LENGTH_LONG).show()
            isValid = false
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = getString(R.string.senha_obrigatorio)
            Toast.makeText(context, getString(R.string.senha_obrigatorio), Toast.LENGTH_LONG).show()
            isValid = false
        }

        return isValid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}