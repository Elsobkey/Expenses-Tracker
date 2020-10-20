package com.sobky.expensestracking.data.repository

import androidx.lifecycle.LiveData
import com.sobky.expensestracking.data.db.dao.ExpensesDao
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems

class ExpensesRepository private constructor(private val expensesDao: ExpensesDao) {

    fun getExpenses(): LiveData<List<ExpenseAndExpenseItems>> {
        return expensesDao.getExpenseAndExpenseItems()
    }

    suspend fun createExpense(expense: Expense): Long {
        return expensesDao.insertExpense(expense)
    }

    companion object {

        // For singleton instantiation
        @Volatile
        private var instance: ExpensesRepository? = null

        fun getInstance(expensesDao: ExpensesDao) =
            instance ?: synchronized(this) {
                instance ?: ExpensesRepository(expensesDao).also {
                    instance = it
                }
            }
    }
}