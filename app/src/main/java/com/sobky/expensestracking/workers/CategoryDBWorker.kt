package com.sobky.expensestracking.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sobky.expensestracking.data.db.AppDatabase
import com.sobky.expensestracking.util.CATEGORY_DATA

class CategoryDBWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result =
        try {
            AppDatabase.getInstance(applicationContext).categoryDao().insertAll(CATEGORY_DATA)
            Log.v(TAG, "Done")
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error in inserting  database", ex)
            Result.failure()
        }

    companion object {
        private const val TAG = "CategoryDBWorker"
    }
}