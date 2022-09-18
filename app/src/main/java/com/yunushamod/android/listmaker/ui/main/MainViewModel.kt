package com.yunushamod.android.listmaker.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.yunushamod.android.listmaker.models.TaskList

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    lateinit var onListAdded: (() -> Unit)
    val lists: MutableList<TaskList> by lazy{
        retrieveLists()
    }

    private fun retrieveLists(): MutableList<TaskList>{
        val sharedPreferencesContent = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()
        for(taskList in sharedPreferencesContent){
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            taskLists += list
        }
        return taskLists
    }

    fun saveList(list: TaskList){
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }

    fun updateList(list: TaskList){
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
    }

    fun refreshLists(){
        lists.clear()
        lists.addAll(retrieveLists())
    }
}