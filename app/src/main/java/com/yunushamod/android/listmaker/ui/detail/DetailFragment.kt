package com.yunushamod.android.listmaker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunushamod.android.listmaker.databinding.FragmentDetailBinding

class DetailFragment private constructor() : Fragment() {
    private val viewModel: ListDetailViewModel by lazy{
        ViewModelProvider(requireActivity())[ListDetailViewModel::class.java]
    }
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.taskList.layoutManager = LinearLayoutManager(context)
        val taskList = viewModel.task
        val adapter = DetailListAdapter(taskList)
        binding.taskList.adapter = adapter
        viewModel.onTaskAdded = {
            adapter.onTaskAdded()
        }
    }

    companion object{
        fun newInstance() = DetailFragment()
    }
}