package com.example.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task.R
import com.example.task.databinding.FragmentRecoverAccountBinding
import com.example.task.ui.BaseFragment
import com.example.task.utils.FirebaseHelper
import com.example.task.utils.initToolbar
import com.example.task.utils.showBottomSheet

class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonRecoverAccount.setOnClickListener {
            validateData()
        }
    }

    private fun recoverAccount(email: String) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                binding.progressBar.isVisible = false
                if (task.isSuccessful) {
                    showBottomSheet(message = getString(R.string.enviou_link_recuperacao))
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.erro_recuperar) + ": " + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateData() {
        val email = binding.editTextEmail.text.toString()
        var isValid = true
        if (email.isEmpty()) {
            binding.editTextEmail.error = getString(R.string.email_obrigatorio)
            showBottomSheet(message = getString(R.string.email_obrigatorio))
            isValid = false
        }

        if (isValid) {
            hideKeyboard()
            binding.progressBar.isVisible = true
            recoverAccount(email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}