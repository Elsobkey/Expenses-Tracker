package com.sobky.expensestracking.ui.expenseitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobky.expensestracking.data.repository.ExpenseRepository

/**
 * Factory for creating a [ExpenseItemsViewModel] with a constructor that takes a
 * no parameters for now.
 */
class ExpenseItemsViewModelFactory(
    val expenseId: Long,
    private val repository: ExpenseRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpenseItemsViewModel(
            expenseId,
            repository
        ) as T
    }
}