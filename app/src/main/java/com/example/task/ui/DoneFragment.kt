package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.adapter.TaskAdapter
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentDoingBinding
import com.example.task.databinding.FragmentDoneBinding

class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewTask()
        getTask()
    }


    private fun initRecyclerViewTask() {
        taskAdapter =
            TaskAdapter(requireContext()) { task, option ->
                taskAdapter.optionSelected(
                    task,
                    option
                )
            }

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }
    private fun getTask() {
        taskAdapter.submitList(
            listOf(
                Task("0", "Criar nova tela", StatusTask.DONE),
                Task("1", "Criar nova pagina", StatusTask.DONE),
                Task("2", "Salvar task", StatusTask.DONE),
                Task("3", "Remover task", StatusTask.DONE),
                Task("4", "Alterar task", StatusTask.DONE),
                Task("5", "Listar task", StatusTask.DONE),
                Task("6", "Criar nova tela", StatusTask.DONE),
                Task("7", "Criar nova pagina", StatusTask.DONE),
                Task("8", "Salvar task", StatusTask.DONE),
                Task("9", "Remover task", StatusTask.DONE),
                Task("10", "Alterar task", StatusTask.DONE),
                Task("11", "Listar task", StatusTask.DONE),
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}