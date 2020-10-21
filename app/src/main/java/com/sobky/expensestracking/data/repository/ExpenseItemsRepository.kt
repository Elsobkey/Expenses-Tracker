package com.sobky.expensestracking.data.repository

import androidx.lifecycle.LiveData
import com.sobky.expensestracking.data.db.dao.ExpenseItemsDao
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory

/**
 * Repository module for handling data operations.
 */
class ExpenseItemsRepository private constructor(private val dao: ExpenseItemsDao) {

    fun getExpenseItem(id: Long): ExpenseItem {
        return dao.getExpenseItem(id)
    }

    fun getExpenseItems(id: Long): LiveData<List<ExpenseItemAndCategory>> {
        return dao.getExpenseItems(id)
    }

    suspend fun createExpenseItem(expense: ExpenseItem): Long {
        return dao.insertExpenseItem(expense)
    }

    suspend fun updateExpenseItem(expenseItem: ExpenseItem): Int {
        return dao.updateExpenseItem(expenseItem)
    }

    suspend fun deleteExpenseItem(expenseItem: ExpenseItem): Int {
        return dao.deleteExpenseItem(expenseItem)
    }

    suspend fun getExpense(id: Long): Expense {
        return dao.getExpense(id)
    }

    suspend fun updateExpense(expense: Expense): Int {
        return dao.updateExpense(expense)
    }

    companion object {

        // For singleton instantiation
        @Volatile
        private var instance: ExpenseItemsRepository? = null

        fun getInstance(expenseItemsDao: ExpenseItemsDao) =
            instance ?: synchronized(this) {
                instance ?: ExpenseItemsRepository(expenseItemsDao).also { instance = it }
            }
    }
}

