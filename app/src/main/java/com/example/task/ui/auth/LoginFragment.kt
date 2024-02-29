package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentLoginBinding
import com.example.task.databinding.FragmentSplashBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.buttonCreateAccount.setOnClickListener { goToCreateAccount() }
        binding.buttonRecoverAccount.setOnClickListener { goToRecoverAccount() }
    }

    private fun goToCreateAccount() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun goToRecoverAccount() {
        findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}