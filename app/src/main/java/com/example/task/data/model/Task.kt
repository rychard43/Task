package com.example.task.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class StatusTask {
    TODO,
    DOING,
    DONE
}

@Parcelize
data class Task(
    val id: String,
    val description: String,
    val status: StatusTask
) : Parcelable
