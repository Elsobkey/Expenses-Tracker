package com.sobky.expensestracking.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory

@Dao
interface ExpenseItemsDao {

    @Query("SELECT * FROM ExpenseItem")
    fun getExpenseItems(): LiveData<List<ExpenseItem>>

    @Query("SELECT * FROM ExpenseItem WHERE expenseId = :id") // ignore items with empty data
    //@Query("SELECT * FROM ExpenseItem WHERE expenseId = :id and (expenseName != '' OR (AMOUNT != '' AND (AMOUNT > 0)) OR (price != '' AND price > 0) OR placeName != '' OR PlaceAddress != '' OR description != '')")
    fun getExpenseItems(id: Long): LiveData<List<ExpenseItemAndCategory>>

    @Query("SELECT * FROM ExpenseItem WHERE id =:id")
    fun getExpenseItem(id: Long): ExpenseItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenseItem(expenseItem: ExpenseItem): Long

    @Update
    suspend fun updateExpenseItem(expenseItem: ExpenseItem): Int

    @Delete
    suspend fun deleteExpenseItem(expenseItem: ExpenseItem): Int

    // TODO : repeated in [com.sobky.expensestracking.data.db.dao.ExpensesDao]
    @Query("SELECT * FROM Expense WHERE id =:id")
    suspend  fun getExpense(id: Long): Expense

    @Update
    suspend fun updateExpense(expense: Expense): Int

    @Query("DELETE FROM ExpenseItem WHERE (expenseName IS NULL OR expenseName = '') AND ((AMOUNT IS NULL OR AMOUNT = '') OR (AMOUNT <= 0)) AND ((price IS NULL OR price = '') OR (price <= 0)) AND (placeName IS NULL OR placeName = '') AND (PlaceAddress IS NULL OR PlaceAddress = '') AND  (description IS NULL OR description = '')")
    suspend fun deleteAllEmptyExpenseItems(): Int

}