package com.yunushamod.android.listmaker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yunushamod.android.listmaker.databinding.ListSelectionViewHolderBinding
import com.yunushamod.android.listmaker.models.TaskList

class ListSelectionAdapter(private val listOfTasks: List<TaskList>, private val listSelectionClickHandler: ListSelectionClickHandler?)
    : RecyclerView.Adapter<ListSelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemNumber.text = (position+1).toString()
        holder.binding.itemString.text  = listOfTasks[position].name
        listSelectionClickHandler?.let {
            holder.itemView.setOnClickListener{_->
                it.onTaskListClicked(listOfTasks[position])
            }
        }
    }

    override fun getItemCount(): Int = listOfTasks.size
    fun listsUpdated(){
        notifyItemInserted(listOfTasks.size - 1)
    }
}


/**
 * A simple interface that helps pass a `TaskList` object from `MainFragment` to the hosting activity
 */
interface ListSelectionClickHandler{
    fun onTaskListClicked(taskList: TaskList)
}