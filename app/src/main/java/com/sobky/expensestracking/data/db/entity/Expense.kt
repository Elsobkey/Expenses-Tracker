package com.sobky.expensestracking.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Expense")
data class Expense(
    @ColumnInfo(name = "expensesTitle") val expensesTitle: String = "",
    @ColumnInfo(name = "expensesDate") val expensesDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "userId") val userId: String = "154887" //TODO
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    override fun toString(): String {
        return expensesTitle
    }

}