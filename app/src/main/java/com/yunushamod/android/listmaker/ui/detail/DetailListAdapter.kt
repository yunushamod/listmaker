package com.yunushamod.android.listmaker.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yunushamod.android.listmaker.databinding.DetailListViewHolderBinding
import com.yunushamod.android.listmaker.models.TaskList

class DetailListAdapter(private val taskList: TaskList): RecyclerView.Adapter<DetailListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListViewHolder {
        val binding = DetailListViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailListViewHolder, position: Int) {
        holder.binding.itemNumber.text = (position + 1).toString()
        holder.binding.itemString.text = taskList.tasks[position]
    }

    override fun getItemCount(): Int = taskList.tasks.size
    fun onTaskAdded() {
        notifyDataSetChanged()
    }
}