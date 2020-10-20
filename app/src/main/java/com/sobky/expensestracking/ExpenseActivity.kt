package com.sobky.expensestracking

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sobky.expensestracking.databinding.ActivityExpenseBinding
import com.sobky.expensestracking.util.updateStatusBarColor

class ExpenseActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityExpenseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        updateStatusBarColor(this)
        //toolbarConfig(this)
        setSupportActionBar(binding?.toolbar)
    }

    fun setToolbarTitle(title: String) {
        binding?.tvToolBarTile?.text = title
    }

    fun setHomeButtonEnabled(enabled:Boolean){
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enabled)
            actionBar.setDisplayHomeAsUpEnabled(enabled)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_expense, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }

    override fun onClick(v: View?) {}

    companion object {
        const val ARGS_EXPENSE_ID: String = "ExpenseIdArgs"
        const val ARGS_EXPENSE_TITLE: String = "ExpenseTitleArgs"
    }
}