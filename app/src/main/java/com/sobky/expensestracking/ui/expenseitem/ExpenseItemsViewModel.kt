package com.sobky.expensestracking.ui.expenseitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseItemsRepository
import kotlinx.coroutines.launch

//import com.sobky.expensestracking.data.room.entity.ExpenseItem

class ExpenseItemsViewModel internal constructor(
    val expenseId: Long,
    private val repository: ExpenseItemsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val expenseItems: LiveData<List<ExpenseItemAndCategory>> = repository.getExpenseItems(expenseId)


    fun updateExpenseTitle(expenseId: Long, expenseTitle: String) {
        viewModelScope.launch {
            val expense = repository.getExpense(expenseId)
            expense.expensesTitle = expenseTitle
            repository.updateExpense(expense)
        }

    }

    companion object {
        const val TAG = "ExpenseItemsViewModel"
    }

}