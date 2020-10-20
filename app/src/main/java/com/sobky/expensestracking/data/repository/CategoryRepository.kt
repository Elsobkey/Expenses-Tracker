package com.sobky.expensestracking.data.repository

import androidx.lifecycle.LiveData
import com.sobky.expensestracking.data.db.dao.CategoryDao
import com.sobky.expensestracking.data.db.entity.Category

/**
 * Repository module for handling data operations.
 */
class CategoryRepository private constructor(private val dao: CategoryDao) {


    fun getCategories(): LiveData<List<Category>> {
        return dao.getCategories()
    }

    fun getCategory(id: Int): LiveData<Category> {
        return dao.getCategory(id)
    }

    companion object {

        // For singleton instantiation
        @Volatile
        private var instance: CategoryRepository? = null

        fun getInstance(dao: CategoryDao) =
            instance ?: synchronized(this) {
                instance ?: CategoryRepository(dao).also { instance = it }
            }
    }
}

