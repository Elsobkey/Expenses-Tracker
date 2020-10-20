package com.sobky.expensestracking

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sobky.expensestracking.ExpenseActivity.Companion.ARGS_EXPENSE_ID
import com.sobky.expensestracking.ExpenseActivity.Companion.ARGS_EXPENSE_TITLE
import com.sobky.expensestracking.util.updateStatusBarColor
import java.lang.Deprecated


@Deprecated
class ExpenseItemsActivity : AppCompatActivity() {

    private var expenseId: Long = 0
    private lateinit var expenseTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_items)

        updateStatusBarColor(this)
        //toolbarConfig(this)

        receiveArgs()
    }

    private fun receiveArgs() {
        expenseId = intent?.getLongExtra(ARGS_EXPENSE_ID, 0L) ?: 0L
        expenseTitle = intent?.getStringExtra(ARGS_EXPENSE_TITLE) ?: ""
    }

    fun getExpenseId() = expenseId

    fun getExpenseTitle() = expenseTitle

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_empty, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

    companion object {
        const val TAG = "ExpenseItemsActivity"
    }
}