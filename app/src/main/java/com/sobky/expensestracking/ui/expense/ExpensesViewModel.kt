package com.sobky.expensestracking.ui.expense

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobky.expensestracking.data.repository.ExpensesRepository
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems
import kotlinx.coroutines.launch

class ExpensesViewModel(var repository: ExpensesRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val expenses: LiveData<List<ExpenseAndExpenseItems>> = repository.getExpenses()

    fun createExpense(expenseTitle:String) {
        viewModelScope.launch {
            val createdExpenseId:Long = repository.createExpense(Expense(expenseTitle, userId = "1"))
            Log.v(TAG, createdExpenseId.toString())
        }
    }

companion object{
    const val TAG = "ExpensesViewModel"
}

}