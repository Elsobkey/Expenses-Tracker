package com.sobky.expensestracking.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobky.expensestracking.data.db.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM Category")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM Category WHERE id =:id")
    fun getCategory(id: Int): LiveData<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
}