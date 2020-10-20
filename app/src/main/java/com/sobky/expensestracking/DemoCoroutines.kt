package com.sobky.expensestracking

import android.util.Log
import kotlinx.coroutines.*

class DemoCoroutines {

    suspend fun fetchUser() {
        return GlobalScope.async(Dispatchers.IO) {
            // make network call
            // return user
        }.await()
    }

    suspend fun fetchUserKTX() {
        return withContext(Dispatchers.IO + handler) {
            // make network call
            // return user
        }
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("TAG", "$exception handled !")
    }
}