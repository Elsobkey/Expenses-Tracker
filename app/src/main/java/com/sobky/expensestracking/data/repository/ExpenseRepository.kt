package com.sobky.expensestracking.data.repository

import androidx.lifecycle.LiveData
import com.sobky.expensestracking.data.db.dao.ExpenseDao
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory

class ExpenseRepository private constructor(private val dao: ExpenseDao) {

    fun getExpenses(): LiveData<List<ExpenseAndExpenseItems>> {
        return dao.getExpenseAndExpenseItems()
    }

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

    suspend fun deleteExpense(expense: Expense): Int {
        return dao.deleteExpense(expense)
    }

    suspend fun deleteExpenseItem(expenseItem: ExpenseItem): Int {
        return dao.deleteExpenseItem(expenseItem)
    }

    suspend fun getExpense(id: Long): Expense {
        return dao.getExpense(id)
    }

   fun getLatestCreatedExpense(): LiveData<ExpenseAndExpenseItems> {
        return dao.getLatestCreatedExpense()
    }

    suspend fun getExpenseAndExpenseItems(id: Long): ExpenseAndExpenseItems {
        return dao.getExpenseAndExpenseItems(id)
    }

    suspend fun updateExpense(expense: Expense): Int {
        return dao.updateExpense(expense)
    }


    companion object {

        // For singleton instantiation
        @Volatile
        private var instance: ExpenseRepository? = null

        fun getInstance(expensesDao: ExpenseDao) =
            instance ?: synchronized(this) {
                instance ?: ExpenseRepository(expensesDao).also {
                    instance = it
                }
            }
    }
}