package com.sobky.expensestracking.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Expense")
data class Expense(
    @ColumnInfo(name = "expensesTitle") var expenseTitle: String = "",
    @ColumnInfo(name = "expensesDate") val expensesDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "userId") val userId: String = "154887", //TODO
    val totalExpensePrice: Double = 0.0 /* react as field not a real column (don't have the real total expense price)for return total expense price while query..*/
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    override fun toString(): String {
        return expenseTitle
    }
}