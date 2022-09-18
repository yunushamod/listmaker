package com.yunushamod.android.listmaker.ui.detail

import androidx.lifecycle.ViewModel
import com.yunushamod.android.listmaker.models.TaskList

class ListDetailViewModel : ViewModel() {
    lateinit var task: TaskList
    lateinit var onTaskAdded: () -> Unit
    fun addTask(taskName: String){
        task.tasks.add(taskName)
        onTaskAdded.invoke()
    }
}