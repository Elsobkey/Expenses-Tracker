package com.sobky.expensestracking.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM Expense")
    fun getExpenses(): LiveData<List<Expense>>

    /**
     * This query will tell Room to query both the [Expense] and [ExpenseItem] tables and handle
     * the object mapping.
     */
    //@Transaction
    //@Query("SELECT * FROM Expense WHERE id IN (SELECT DISTINCT(expenseId) FROM ExpenseItem)")
    @Query("SELECT * FROM Expense")
    fun getExpenseAndExpenseItems(): LiveData<List<ExpenseAndExpenseItems>>

    @Query("SELECT * FROM ExpenseItem where categoryId = :categoryId")
    fun getExpenseDetailsWithSpecificCategory(categoryId: Int): LiveData<List<ExpenseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

}