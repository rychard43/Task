package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.adapter.TaskAdapter
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentDoingBinding
import com.example.task.databinding.FragmentHomeBinding


class DoingFragment : Fragment() {
    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewTask(getTask())
    }


    private fun initRecyclerViewTask(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.setHasFixedSize(true)
        binding.rvTasks.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0", "Criar nova tela", StatusTask.DOING),
        Task("1", "Criar nova pagina", StatusTask.DOING),
        Task("2", "Salvar task", StatusTask.DOING),
        Task("3", "Remover task", StatusTask.DOING),
        Task("4", "Alterar task", StatusTask.DOING),
        Task("5", "Listar task", StatusTask.DOING),
        Task("6", "Criar nova tela", StatusTask.DOING),
        Task("7", "Criar nova pagina", StatusTask.DOING),
        Task("8", "Salvar task", StatusTask.DOING),
        Task("9", "Remover task", StatusTask.DOING),
        Task("10", "Alterar task", StatusTask.DOING),
        Task("11", "Listar task", StatusTask.DOING),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}