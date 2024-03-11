package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.task.R
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentFormTaskBinding
import com.example.task.utils.FirebaseHelper
import com.example.task.utils.initToolbar
import com.example.task.utils.showBottomSheet

class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var task: Task
    private var statusTask: StatusTask = StatusTask.TODO
    private var isNewTask = true

    private val args: FormTaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels()


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
        getArgs()
        initListeners()
    }

    private fun getArgs() {
        args.task.let { task ->
            if (task != null) {
                this.task = task
                configTask()
            }
        }
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

    private fun configTask() {
        isNewTask = false
        statusTask = task.status
        binding.titleScreenFormTask.text = getString(R.string.editando_task)
        binding.editTextDescription.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        val id = when (task.status) {
            StatusTask.TODO -> R.id.radioButtonTodo
            StatusTask.DOING -> R.id.radioButtonDoing
            else -> R.id.radioButtonDone
        }
        binding.radioGroup.check(id)
    }

    private fun validateData() {
        val descricao = binding.editTextDescription.text.toString()
        if (descricao.isEmpty()) {
            binding.editTextDescription.error = getString(R.string.descricao_obrigatorio)
            showBottomSheet(message = getString(R.string.descricao_obrigatorio))
        } else {
            binding.progressBar.isVisible = true
            if (isNewTask) {
                task = Task()
            }
            task.description = descricao
            task.status = statusTask
            saveTask()
        }
    }

    private fun saveTask() {
        FirebaseHelper.getDatabaseReference()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    if (isNewTask) {
                        showToast(getString(R.string.salvo_sucesso))
                        findNavController().popBackStack()
                    } else {
                        viewModel.updateTask(task)
                        showToast(getString(R.string.atualizada_sucesso))
                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()
                    }
                } else {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = getString(R.string.erro_generico))
                }
            }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}