package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.adapter.TaskAdapter
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {


    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initRecyclerViewTask(getTask())
    }

    private fun initListeners() {
        binding.floatingActionButtonADDTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment)
        }
    }

    private fun initRecyclerViewTask(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.setHasFixedSize(true)
        binding.rvTasks.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0", "Criar nova tela", StatusTask.TODO),
        Task("1", "Criar nova pagina", StatusTask.TODO),
        Task("2", "Salvar task", StatusTask.TODO),
        Task("3", "Remover task", StatusTask.TODO),
        Task("4", "Alterar task", StatusTask.TODO),
        Task("5", "Listar task", StatusTask.TODO),
        Task("6", "Criar nova tela", StatusTask.TODO),
        Task("7", "Criar nova pagina", StatusTask.TODO),
        Task("8", "Salvar task", StatusTask.TODO),
        Task("9", "Remover task", StatusTask.TODO),
        Task("10", "Alterar task", StatusTask.TODO),
        Task("11", "Listar task", StatusTask.TODO),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}