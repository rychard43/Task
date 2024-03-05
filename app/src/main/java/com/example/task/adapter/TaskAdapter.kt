package com.example.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val taskList: List<Task>
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textDescriptionCard.text = task.description
        setIndicators(task, holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {
            StatusTask.TODO -> holder.binding.buttonBack.isVisible = false
            StatusTask.DONE -> holder.binding.buttonNext.isVisible = false
            StatusTask.DOING -> {
                holder.binding.buttonBack.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.color_status_todo
                    )
                )
                holder.binding.buttonNext.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.color_status_done
                    )
                )
            }
        }

    }

    override fun getItemCount() = taskList.size

    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

}