package com.sobky.expensestracking.data.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.sobky.expensestracking.data.db.entity.Category
import com.sobky.expensestracking.data.db.entity.ExpenseItem

/**
 * This class captures the relationship between a [ExpenseItem] and a Category [com.sobky.expensestracking.data.db.entity.Category], which is
 * used by Room to fetch the related entities.
 */
data class ExpenseItemAndCategory(
    @Embedded
    val expenseItem: ExpenseItem,

    @Relation(parentColumn = "categoryId", entityColumn = "id")
    val category: Category?
)