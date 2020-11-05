package com.sobky.expensestracking.ui.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems
import com.sobky.expensestracking.data.repository.ExpenseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExpensesViewModel(var repository: ExpenseRepository) : ViewModel() {

    val expenses: LiveData<List<ExpenseAndExpenseItems>> = repository.getExpenses()

    // check if current expense has empty data (if expense or expenseItem in expenseItems list has any data, so it was rich not empty)
    private val _isEmptyExpense: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEmptyExpenseObservable: LiveData<Boolean>
        get() = _isEmptyExpense

    fun isEmptyExpense(expenseId: Long) {
        if (expenseId > 0) {
            viewModelScope.launch {
                val expense: ExpenseAndExpenseItems? =
                    repository.getExpenseAndExpenseItems(expenseId)
                expense?.let {
                    _isEmptyExpense.value = checkIfExpenseHasEmptyData(expense)
                }
            }
        }
    }

    private fun checkIfExpenseHasEmptyData(expense: ExpenseAndExpenseItems): Boolean {
        var emptyExpense = expense.expense.expenseTitle.trim().isEmpty()
        if (emptyExpense) {
            if (!expense.expenseItems.isNullOrEmpty()) {
                for (expenseItem in expense.expenseItems) {
                    if (ExpenseItem.isRichExpenseItem(expenseItem)) {
                        emptyExpense = false
                        break
                    }
                }
            }
        }
        return emptyExpense
    }

    fun deleteExpense(expenseId: Long) {
        var deletedRows = 0
        viewModelScope.launch {
            val expense: Expense? = repository.getExpense(expenseId)
            expense?.let {
                deletedRows = repository.deleteExpense(expense)
            }
        }
        _isEmptyExpense.value = false
    }

    companion object {
        const val TAG = "ExpensesViewModel"
    }
}