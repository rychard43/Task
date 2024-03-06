package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentFormTaskBinding
import com.example.task.utils.initToolbar
import com.example.task.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var task: Task
    private var statusTask: StatusTask = StatusTask.TODO
    private var isNewTask = true
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reference = Firebase.database.reference
        auth = Firebase.auth
        initToolbar(binding.toolbar)
        initListeners()
    }

    private fun initListeners() {
        binding.buttonSalvar.setOnClickListener {
            validateData()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radioButtonTodo -> statusTask = StatusTask.TODO
                R.id.radioButtonDoing -> statusTask = StatusTask.DOING
                R.id.radioButtonDone -> statusTask = StatusTask.DONE
            }
        }
    }

    private fun validateData() {
        val descricao = binding.editTextDescription.text.toString()
        if (descricao.isEmpty()) {
            binding.editTextDescription.error = getString(R.string.descricao_obrigatorio)
            showBottomSheet(message = getString(R.string.descricao_obrigatorio))
        } else {
            binding.progressBar.isVisible = true
            if (isNewTask) task = Task()
            task.id = reference.database.reference.push().key ?: ""
            task.description = descricao
            task.status = statusTask
            saveTask()
        }
    }

    private fun saveTask() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.salvo_sucesso),
                        Toast.LENGTH_SHORT
                    ).show()
                    if (isNewTask) {
                        findNavController().popBackStack()
                    } else {
                        binding.progressBar.isVisible = false
                    }
                } else {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.erro_generico))
                }
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}