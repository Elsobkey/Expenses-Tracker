package com.sobky.expensestracking.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sobky.expensestracking.data.db.converter.Converters
import com.sobky.expensestracking.data.db.dao.CategoryDao
import com.sobky.expensestracking.data.db.dao.ExpenseItemsDao
import com.sobky.expensestracking.data.db.dao.ExpensesDao
import com.sobky.expensestracking.data.db.entity.Category
import com.sobky.expensestracking.data.db.entity.Expense
import com.sobky.expensestracking.data.db.entity.ExpenseItem
import com.sobky.expensestracking.utils.DATABASE_NAME
import com.sobky.expensestracking.worker.CategoryDBWorker


/**
 * The Room database for this app
 */
@Database(
    entities = [Expense::class, ExpenseItem::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpensesDao
    abstract fun expenseItemsDao(): ExpenseItemsDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<CategoryDBWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                //.addMigrations(MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .build()
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table
                database.execSQL(
                    "CREATE TABLE ExpenseItem_New (id INTEGER, expenseId INTEGER, expenseName TEXT, price TEXT,amount TEXT, unitId TEXT, categoryId INTEGER, description TEXT,placeName TEXT, placeAddress TEXT, lat REAL, lng REAL, createdDate TEXT,  PRIMARY KEY(id), FOREIGN KEY (expenseId) REFERENCES Expense (id))"
                )
                // Copy the data
                database.execSQL(
                    "INSERT INTO ExpenseItem_New (expenseId, expenseName, price, amount, unitId, categoryId, description, placeName, placeAddress, lat, lng, createdDate, id ) SELECT expenseId, expenseName, price as text, amount as text, unitId, categoryId, description, placeName, placeAddress, lat, lng , createdDate , id  FROM ExpenseItem"
                )
                // delete the old table
                database.execSQL("DROP TABLE ExpenseItem")
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE ExpenseItem_New RENAME TO ExpenseItem")
            }
        }
    }
}