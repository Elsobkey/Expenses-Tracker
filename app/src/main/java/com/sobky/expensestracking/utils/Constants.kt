package com.sobky.expensestracking.utils

import com.sobky.expensestracking.data.db.entity.Category

/**
 * Constants used throughout the app.
 */
const val DATABASE_NAME = "expenses-tracking-db"

//val dateFormat = SimpleDateFormat("MMM d, yyyy HH:mm a", Locale.US)

val CATEGORY_DATA = listOf(
    Category("Charity"),
    Category("Installments & Association"), // جمعيات واقساط
    Category("Gaz"),
    Category("SuperMarket"),
    Category("Vegetables & Fruits"),
    Category("Cleaners"),
    Category("Clothes and Shoes"),
    Category("Doctors & Medicine"),
    Category("Car Maintenance"),
    Category("Family Expenses"), // مصروف الاولاد
    Category("Mobile & Internet"),
    Category("Bread"),
    Category("Sweets"),
    Category("Pastry"), // معجنات من الفرن
    Category("Seeds"), // لب
    Category("Fast Foods"),
    Category("Wedding Gifts"), // نقوط الأفراح والنجاح
    Category("Non-fixed Expenses"),
    Category("Others")
)