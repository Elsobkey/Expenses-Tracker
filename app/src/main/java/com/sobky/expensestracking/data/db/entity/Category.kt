package com.sobky.expensestracking.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(var categoryName: String = "") {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "The ID is '$id' and  categoryName is '$categoryName' "
    }

}