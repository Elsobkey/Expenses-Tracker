package com.sobky.expensestracking.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobky.expensestracking.data.repository.ExpenseRepository

class ExpenseViewModelFactory(var expensesRepository: ExpenseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(
            expensesRepository
        ) as T
    }
}