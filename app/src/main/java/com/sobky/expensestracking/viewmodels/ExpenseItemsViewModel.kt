package com.sobky.expensestracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseItemsRepository
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory

//import com.sobky.expensestracking.data.room.entity.ExpenseItem

class ExpenseItemsViewModel internal constructor(
    val expenseId: Long,
    private val repository: ExpenseItemsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val expenseItems: LiveData<List<ExpenseItemAndCategory>> = repository.getExpenseItems(expenseId)


    companion object {
        const val TAG = "ExpenseItemsViewModel"
    }

}