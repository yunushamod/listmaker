package com.yunushamod.android.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.yunushamod.android.listmaker.databinding.ActivityMainBinding
import com.yunushamod.android.listmaker.models.TaskList
import com.yunushamod.android.listmaker.ui.main.MainFragment
import com.yunushamod.android.listmaker.ui.main.*

class MainActivity : AppCompatActivity(), ListSelectionClickHandler {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this)))[MainViewModel::class.java]
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        binding.floatingActionButton.setOnClickListener{
            showCreateListDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            data?.let {
                mainViewModel.updateList(data.getParcelableExtra(RETURN_KEY)!!)
                mainViewModel.refreshLists()
            }
        }
    }

    private fun showCreateListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val builder = AlertDialog.Builder(this)
        val listTitle = EditText(this)
        listTitle.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitle)
        builder.setPositiveButton(positiveButtonTitle){
            dialog, _ ->
            dialog.dismiss()
            val taskList = TaskList(listTitle.text.toString())
            mainViewModel.saveList(taskList)
            showListDetail(taskList)
        }
        builder.create().show()
    }

    private fun showListDetail(taskList: TaskList){
        val listDetailIntent = DetailActivity.newIntent(this, taskList)
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    override fun onTaskListClicked(taskList: TaskList) {
        showListDetail(taskList)
    }

    companion object{
        private const val LIST_DETAIL_REQUEST_CODE = 0
        const val RETURN_KEY = "list"
    }
}