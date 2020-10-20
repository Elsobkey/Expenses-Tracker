package com.sobky.expensestracking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobky.expensestracking.data.repository.ExpensesRepository

class ExpensesViewModelFactory(var expensesRepository: ExpensesRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpensesViewModel(
            expensesRepository
        ) as T
    }
}