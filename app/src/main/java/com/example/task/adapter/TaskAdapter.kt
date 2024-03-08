package com.example.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.model.StatusTask
import com.example.task.data.model.Task
import com.example.task.databinding.ItemTaskBinding
import com.example.task.utils.showBottomSheet

class TaskAdapter(
    private val context: Context,
    private val taskSelected: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val SELECTED_BACK: Int = 1
        const val SELECTED_NEXT: Int = 2
        const val SELECTED_REMOVE: Int = 3
        const val SELECTED_DETAILS: Int = 4
        const val SELECTED_EDIT: Int = 5

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id && oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem && oldItem.description == newItem.description
            }
        }
    }

    fun optionSelected(task: Task, option: Int, callback: (task: Task) -> Unit,fragment: Fragment) {
        when (option) {
            SELECTED_BACK -> {
                Toast.makeText(context, "Back " + task.description, Toast.LENGTH_SHORT).show()
            }

            SELECTED_REMOVE -> {
                fragment.showBottomSheet(
                    titleDialog = fragment.getString(R.string.deseja_remover),
                    titleButton = fragment.getString(R.string.confirmar),
                    message = fragment.getString(R.string.aperte_confirmar_remover_tarefa),
                    onClick ={
                        callback(task)
                    }
                )
            }

            SELECTED_DETAILS -> {
                Toast.makeText(context, "Details " + task.description, Toast.LENGTH_SHORT).show()

            }

            SELECTED_EDIT -> {
                Toast.makeText(context, "Edit " + task.description, Toast.LENGTH_SHORT).show()

            }

            SELECTED_NEXT -> {
                Toast.makeText(context, "Next " + task.description, Toast.LENGTH_SHORT).show()

            }
        }
    }


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
        val task = getItem(position)
        holder.binding.textDescriptionCard.text = task.description
        setIndicators(task, holder)
        holder.binding.buttonRemove.setOnClickListener {
            taskSelected(task, SELECTED_REMOVE)
        }
        holder.binding.buttonDetails.setOnClickListener {
            taskSelected(task, SELECTED_DETAILS)
        }
        holder.binding.buttonEdit.setOnClickListener {
            taskSelected(task, SELECTED_EDIT)
        }
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {
            StatusTask.TODO -> {
                holder.binding.buttonBack.isVisible = false
                holder.binding.buttonNext.setOnClickListener {
                    taskSelected(task, SELECTED_NEXT)
                }

            }

            StatusTask.DONE -> {
                holder.binding.buttonNext.isVisible = false
                holder.binding.buttonBack.setOnClickListener {
                    taskSelected(task, SELECTED_BACK)
                }
            }

            StatusTask.DOING -> {
                holder.binding.buttonBack.setOnClickListener {
                    taskSelected(task, SELECTED_BACK)
                }
                holder.binding.buttonNext.setOnClickListener {
                    taskSelected(task, SELECTED_NEXT)
                }
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

    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

}