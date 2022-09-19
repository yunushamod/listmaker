package com.yunushamod.android.listmaker.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunushamod.android.listmaker.databinding.FragmentMainBinding

/**
 * Any activity hosting this fragment must implement the `ListSelectionClickHandler` interface
 */
class MainFragment : Fragment() {
    private var listSelectionClickHandler: ListSelectionClickHandler? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(requireActivity(), MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity())))[MainViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.listRecyclerView.adapter = ListSelectionAdapter(emptyList(), null)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listRecycler = ListSelectionAdapter(viewModel.lists, listSelectionClickHandler)
        binding.listRecyclerView.adapter = listRecycler
        viewModel.onListAdded = {
            listRecycler.listsUpdated()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listSelectionClickHandler = context as ListSelectionClickHandler
    }

    override fun onDetach() {
        super.onDetach()
        listSelectionClickHandler = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}