package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.task.R
import com.example.task.databinding.FragmentFormTaskBinding
import com.example.task.utils.initToolbar
import com.example.task.utils.showBottomSheet

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSalvar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(): Boolean {
        val descricao = binding.editTextDescription.text.toString()
        var isValid = true
        if (descricao.isEmpty()) {
            binding.editTextDescription.error = getString(R.string.descricao_obrigatorio)
            showBottomSheet(message = getString(R.string.descricao_obrigatorio))
            isValid = false
        }
        return isValid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}