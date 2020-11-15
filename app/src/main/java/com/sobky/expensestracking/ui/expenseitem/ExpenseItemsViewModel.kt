package com.sobky.expensestracking.ui.expenseitem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory
import com.sobky.expensestracking.data.repository.ExpenseRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

//TODO:must understand RepoViewModel in Github demo...
class ExpenseItemsViewModel internal constructor(
    private var expenseId: Long,
    private val repository: ExpenseRepository
) : ViewModel() {

    // make it as LiveData in order not to let any one outside [ExpenseItemsViewModel] rewrite it...
    private var _currentExpense: MutableLiveData<Expense> = MutableLiveData()
    val currentExpense: LiveData<Expense>
        get() = _currentExpense

    val expenseItems: LiveData<List<ExpenseItemAndCategory>>
        get() = repository.getExpenseItems(expenseId)

    init {
        viewModelScope.launch {
            if (expenseId == 0L) expenseId = repository.createExpense(Expense())
            val newCreatedExpense = repository.getExpense(expenseId)
            _currentExpense.value = newCreatedExpense
        }
    }

    fun updateExpenseTitle(expenseTitle: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            //val expense = repository.getExpense(expenseId)
            //val expenseTitleOld = expense.expensesTitle
            //Log.v(TAG,"expense is ${expense.toString()} and id is $expense.expenseId ")
            currentExpense.value?.let {
                it.expenseTitle = expenseTitle
                //Log.v(TAG,"expense is now  ${expense.toString()} instead of $expenseTitleOld")
                repository.updateExpense(it)
            }
        }
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.v(TAG, "Exception caught", throwable)
        }

    companion object {
        const val TAG = "ExpenseItemsViewModel"
    }
}