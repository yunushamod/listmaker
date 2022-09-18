package com.yunushamod.android.listmaker

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.yunushamod.android.listmaker.databinding.ActivityDetailBinding
import com.yunushamod.android.listmaker.models.TaskList
import com.yunushamod.android.listmaker.ui.detail.DetailFragment
import com.yunushamod.android.listmaker.ui.detail.ListDetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ListDetailViewModel
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ListDetailViewModel::class.java]
        val taskList = intent.getParcelableExtra<TaskList>(TASK_LIST_TAG)!!
        viewModel.task = taskList
        title = taskList.name
        binding.fabButton.setOnClickListener{
            showCreateDialog()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        val bundle = Bundle().apply {
            putParcelable(MainActivity.RETURN_KEY, viewModel.task)
        }
        val returnIntent = Intent().apply {
            putExtras(bundle)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }

    private fun showCreateDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.name_of_task))
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(taskEditText)
        builder.setPositiveButton(R.string.add_task){
            dialog, _ ->
            viewModel.addTask(taskEditText.text.toString())
            dialog.dismiss()
        }
        builder.create().show()
    }


    companion object{
        private const val TASK_LIST_TAG = "task-list"
        fun newIntent(packageContext: Context, taskList: TaskList): Intent{
            return Intent(packageContext, DetailActivity::class.java)
                .apply {
                    putExtra(TASK_LIST_TAG, taskList)
                }
        }
    }
}