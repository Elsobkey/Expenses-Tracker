package com.sobky.expensestracking.data.db.relation

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Relation
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import kotlin.math.roundToInt

/**
 * This class captures the relationship between a [Expense] and a expense lines [ExpenseItem] s, which is
 * used by Room to fetch the related entities.
 */
data class ExpenseAndExpenseItems(
    @Embedded
    val expense: Expense,

    @Relation(parentColumn = "id", entityColumn = "expenseId")
    val expenseItems: List<ExpenseItem> = emptyList(),

//    @Nullable
//    val totalExpensePrice: Double = 0.0
) {

    /** get first 3 items of expense items with limited item max length */
    fun getFormattedExpenseItemsTitles(): String {
        val maxItemTitleDigits = 30
        val expenseItemsTitlesSummary: StringBuilder = StringBuilder()
        if (expenseItems.isNotEmpty()) {
            for (i in expenseItems.indices) {
                if (expenseItems[i].expenseName.trim().isNotEmpty()
                    || (expenseItems[i].amount > 0
                            && expenseItems[i].amount.toDouble() > 0)
                    || (expenseItems[i].price > 0
                            && expenseItems[i].price.toDouble() > 0)
                    || (expenseItems[i].placeName.trim().isNotEmpty())
                    || (expenseItems[i].placeAddress.trim().isNotEmpty())
                ) {
                    var expenseTitle = expenseItems[i].expenseName.trim()
                    when {
                        expenseTitle.isEmpty() -> expenseTitle = expenseItems[i].description.trim()
                        expenseTitle.isEmpty() -> expenseTitle = expenseItems[i].placeName.trim()
                        expenseTitle.isEmpty() -> expenseTitle = expenseItems[i].placeAddress.trim()
                        //expenseTitle.isEmpty() -> expenseTitle = expenseItems[i].amount.trim()
                        //expenseTitle.isEmpty() -> expenseTitle = expenseItems[i].price.trim()
                    }

                    val expenseTitleBuilder: StringBuilder = StringBuilder().apply {
                        if (expenseTitle.length > maxItemTitleDigits) {
                            append(expenseTitle.substring(0, maxItemTitleDigits))
                            append("...")
                        } else {
                            append(expenseTitle)
                        }
                    }
                    expenseItemsTitlesSummary.append(expenseTitleBuilder)
                    if (i == 2) { // just add first 3 items
                        if (expenseItems.size > 3)
                            expenseItemsTitlesSummary.append("\n")
                        expenseItemsTitlesSummary.append("...")
                        break
                    } else {
                        if (i != expenseItems.size - 1)
                            expenseItemsTitlesSummary.append("\n")
                    }
                }
            }
        }
        return expenseItemsTitlesSummary.toString()
    }
}