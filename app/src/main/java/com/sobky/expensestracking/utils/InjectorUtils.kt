package com.sobky.expensestracking.utils

import android.content.Context
import com.sobky.expensestracking.data.db.AppDatabase
import com.sobky.expensestracking.data.repository.CategoryRepository
import com.sobky.expensestracking.data.repository.ExpenseRepository
import com.sobky.expensestracking.ui.expense.ExpenseViewModelFactory
import com.sobky.expensestracking.ui.expenseitem.ExpenseItemsViewModelFactory
import com.sobky.expensestracking.ui.expenseiteminfo.ExpenseItemInfoViewModelFactory


/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getExpenseRepository(context: Context): ExpenseRepository {
        return ExpenseRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).expenseDao()
        )
    }

    private fun getCategoryRepository(context: Context): CategoryRepository {
        return CategoryRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).categoryDao()
        )
    }


    fun provideExpenseViewModelFactory(
        context: Context
    ): ExpenseViewModelFactory {
        return ExpenseViewModelFactory(
            getExpenseRepository(context)
        )
    }

    fun provideExpenseItemsViewModelFactory(
        context: Context,
        expenseId: Long
    ): ExpenseItemsViewModelFactory {
        return ExpenseItemsViewModelFactory(
            expenseId,
            getExpenseRepository(context)
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
            getExpenseRepository(context),
            getCategoryRepository(context)
        )
    }

}