package com.example.task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.data.model.Task

class TaskViewModel: ViewModel() {

    private val _taskUpdate = MutableLiveData<Task>()
    val taskUpdate: LiveData<Task> = _taskUpdate

    fun updateTask(task: Task) {
        _taskUpdate.value = task
    }

}