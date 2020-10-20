package com.sobky.expensestracking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseItemsRepository

/**
 * Factory for creating a [ExpenseItemsViewModel] with a constructor that takes a
 * no parameters for now.
 */
class ExpenseItemsViewModelFactory(
    val expenseId: Long,
    private val repository: ExpenseItemsRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpenseItemsViewModel(
            expenseId,
            repository,
            categoryRepository
        ) as T
    }
}