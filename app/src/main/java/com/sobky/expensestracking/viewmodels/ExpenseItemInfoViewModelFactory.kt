package com.sobky.expensestracking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseItemsRepository

/**
 * Factory for creating a [ExpenseItemInfoViewModel] with a constructor that takes a
 * no parameters for now.
 */
class ExpenseItemInfoViewModelFactory(
    private var expenseId: Long,
    var expenseItemId: Long,
    private val repository: ExpenseItemsRepository,
    private val categoryRepository: CategoryRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseItemInfoViewModel(
            expenseId,
            expenseItemId,
            repository,
            categoryRepository
        ) as T
    }

}