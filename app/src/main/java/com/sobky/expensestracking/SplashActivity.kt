package com.sobky.expensestracking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sobky.expensestracking.utils.updateStatusBarColor
import com.sobky.expensestracking.worker.DeleteExpenseItemWorker

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        updateStatusBarColor(this)

        // at start of application make sure that there are no empty expense items
        val request = OneTimeWorkRequestBuilder<DeleteExpenseItemWorker>().build()
        WorkManager.getInstance(this@SplashActivity).enqueue(request)

        Handler(Looper.myLooper()!!).postDelayed(
            {
                startActivity(Intent(this@SplashActivity, ExpenseActivity::class.java))
                finish()
            },
            SPLASH_TIME_OUT
        )


    }

    companion object {
        const val SPLASH_TIME_OUT = 1000L
    }

}