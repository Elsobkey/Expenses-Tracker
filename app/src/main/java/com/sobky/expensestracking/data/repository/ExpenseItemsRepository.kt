package com.sobky.expensestracking.data.repository

import androidx.lifecycle.LiveData
import com.sobky.expensestracking.data.db.dao.ExpenseDao
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory

/**
 * Repository module for handling data operations.
 */
@Deprecated("use [ExpenseRepository] instead")
class ExpenseItemsRepository private constructor(private val dao: ExpenseDao) {

    suspend fun createExpense(expense: Expense): Long {
        return dao.insertExpense(expense)
    }

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

        fun getInstance(expenseDao: ExpenseDao) =
            instance ?: synchronized(this) {
                instance ?: ExpenseItemsRepository(expenseDao).also { instance = it }
            }
    }
}

