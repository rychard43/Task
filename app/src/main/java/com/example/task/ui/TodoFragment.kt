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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.adapter.TaskAdapter
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.FragmentTodoBinding
import com.example.task.utils.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TodoFragment : Fragment() {


    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()

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
        initRecyclerViewTask()
        getTask()
    }

    private fun observeViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if (updateTask.status == StatusTask.TODO) {
                val oldList = taskAdapter.currentList
                val newList = oldList.toMutableList().apply {
                    find { it.id == updateTask.id }?.description = updateTask.description
                }
                val position = newList.indexOfFirst { it.id == updateTask.id }
                taskAdapter.submitList(newList)
                taskAdapter.notifyItemChanged(position)
            }
        }

    }

    private fun initListeners() {
        binding.floatingActionButtonADDTask.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
        observeViewModel()
    }

    private fun initRecyclerViewTask() {
        taskAdapter =
            TaskAdapter(requireContext()) { task, option ->
                taskAdapter.optionSelected(
                    task,
                    option,
                    callback = {
                        callOption(option = option, task = task)
                    },
                    this
                )
            }

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun getTask() {
        FirebaseHelper.getDatabaseReference()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<Task>()
                    snapshot.children.forEach { taskFirebase ->
                        val task = taskFirebase.getValue(Task::class.java) as Task
                        if (task.status == StatusTask.TODO) {
                            taskList.add(task)
                        }
                    }
                    binding.progressBar.isVisible = false
                    listEmpty(taskList)
                    taskList.reverse()
                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    showToast(getString(R.string.erro_generico) + ": " + error.message)
                }

            })
    }

    private fun callOption(option: Int, task: Task) {
        when (option) {
            TaskAdapter.SELECTED_REMOVE -> {
                deleteTask(task)
            }

            TaskAdapter.SELECTED_DETAILS -> {

            }

            TaskAdapter.SELECTED_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECTED_NEXT -> {

            }

            TaskAdapter.SELECTED_BACK -> {

            }
        }
    }

    private fun deleteTask(task: Task) {
        FirebaseHelper.getDatabaseReference()
            .child("tasks")
            .child(FirebaseHelper.getIdUser())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    showToast(getString(R.string.removeu_task))
                } else {
                    showToast(getString(R.string.erro_generico) + ": " + result.exception?.message)
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.textViewLoading.text = if (taskList.isEmpty()) getString(R.string.sem_task) else ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}