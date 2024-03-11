package com.example.task.data.model

import android.os.Parcelable
import com.example.task.utils.FirebaseHelper
import kotlinx.parcelize.Parcelize

enum class StatusTask {
    TODO,
    DOING,
    DONE
}

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var status: StatusTask = StatusTask.TODO
) : Parcelable{
    init {
        this.id = FirebaseHelper.getDatabaseReference().database.reference.push().key ?: ""
    }
}
