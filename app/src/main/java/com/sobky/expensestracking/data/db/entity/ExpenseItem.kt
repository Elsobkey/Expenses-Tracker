package com.sobky.expensestracking.data.db.entity

import androidx.room.*
import com.sobky.expensestracking.ui.expenseitemdetails.ExpenseItemInfoViewModel
import java.util.*

@Entity(
    tableName = "ExpenseItem",
    foreignKeys = [
        ForeignKey(
            entity = Expense::class,
            parentColumns = ["id"],
            childColumns = ["expenseId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("expenseId")]
)
open class ExpenseItem(
    @ColumnInfo(name = "expenseId") var expenseId: Long = 0, // The main expense header id (foreign key)
    @ColumnInfo(name = "expenseName") var expenseName: String = "",
    @ColumnInfo(name = "price") var price: String = "",
    @ColumnInfo(name = "amount") var amount: String = "",
    @ColumnInfo(name = "unitId") var unitId: String = "",
    @ColumnInfo(name = "categoryId") var categoryId: Int = 0, // category like (grocery, Gas, Clothes,...)
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "placeName") var placeName: String = "", // place where executed this expense (ex: Carfour market)
    @ColumnInfo(name = "PlaceAddress") var placeAddress: String = "",
    @ColumnInfo(name = "lat") var lat: Double = 0.0,
    @ColumnInfo(name = "lng") var lng: Double = 0.0,
    @ColumnInfo(name = "expenseItemCreatedDate") var expenseItemCreatedDate: Calendar = Calendar.getInstance()
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0


    override fun toString(): String {
        return "Id is " +
                "$id and expenseItem title is " +
                "$expenseName with price is " +
                "$price and amount is " +
                "$amount and created date is " +
                ExpenseItemInfoViewModel.dateFormat.format(expenseItemCreatedDate.time)
    }

    companion object {
        //TODO : make this function as extension function
        fun isRichExpenseItem(expenseItem: ExpenseItem): Boolean {
            return (expenseItem.expenseName.trim().isNotEmpty()
                    || expenseItem.price.trim().isNotEmpty()
                    || expenseItem.amount.trim().isNotEmpty()
                    || expenseItem.amount.trim().isNotEmpty()
                    || expenseItem.categoryId > 0
                    || expenseItem.unitId.trim().isNotEmpty()
                    || expenseItem.description.trim().isNotEmpty()
                    || expenseItem.placeName.trim().isNotEmpty()
                    || expenseItem.placeAddress.trim().isNotEmpty()
                    || expenseItem.lat > 0
                    || expenseItem.lng > 0)
        }
    }
}