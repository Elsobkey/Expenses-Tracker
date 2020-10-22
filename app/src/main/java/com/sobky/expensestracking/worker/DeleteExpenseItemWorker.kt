package com.sobky.expensestracking.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sobky.expensestracking.data.db.AppDatabase

/** delete unnecessary empty expenses items at start of the app
 *  in order not to increase database with empty items
 *  and to guarantee that all empty expense items were deleted from DB*/
class DeleteExpenseItemWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result =
        try {
            val deletedRows = AppDatabase.getInstance(applicationContext)
                .expenseDao().deleteAllEmptyExpenseItems()
            Log.v(TAG, "Deletion is ok & no. of deleted rows is $deletedRows")
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error while deleting ", ex)
            Result.failure()
        }

    companion object {
        private const val TAG = "DeleteExpenseItemWorker"
    }
}