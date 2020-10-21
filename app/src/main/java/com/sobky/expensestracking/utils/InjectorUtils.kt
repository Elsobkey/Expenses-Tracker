package com.sobky.expensestracking.utils

import android.content.Context
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseItemsRepository
import com.sobky.expensestracking.data.repository.ExpensesRepository
import com.sobky.expensestracking.data.db.AppDatabase
import com.sobky.expensestracking.ui.expenseitemdetails.ExpenseItemInfoViewModelFactory
import com.sobky.expensestracking.ui.expenseitem.ExpenseItemsViewModelFactory
import com.sobky.expensestracking.ui.expense.ExpensesViewModelFactory


/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getExpenseRepository(context: Context): ExpensesRepository {
        return ExpensesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).expenseDao()
        )
    }

    private fun getExpenseItemsRepository(context: Context): ExpenseItemsRepository {
        return ExpenseItemsRepository.getInstance(
            AppDatabase.getInstance(context).expenseItemsDao()
        )
    }


    private fun getCategoryRepository(context: Context): CategoryRepository {
        return CategoryRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).categoryDao()
        )
    }


    fun provideExpensesViewModelFactory(
        context: Context
    ): ExpensesViewModelFactory {
        return ExpensesViewModelFactory(
            getExpenseRepository(context)
        )
    }

    fun provideExpenseItemsViewModelFactory(
        context: Context,
        expenseId: Long
    ): ExpenseItemsViewModelFactory {
        return ExpenseItemsViewModelFactory(
            expenseId,
            getExpenseItemsRepository(context),
            getCategoryRepository(context)
        )
    }

    fun provideExpenseItemInfoViewModelFactory(
        context: Context,
        expenseId: Long,
        expenseItemId: Long
    ): ExpenseItemInfoViewModelFactory {
        return ExpenseItemInfoViewModelFactory(
            expenseId,
            expenseItemId,
            getExpenseItemsRepository(context),
            getCategoryRepository(context)
        )
    }

}