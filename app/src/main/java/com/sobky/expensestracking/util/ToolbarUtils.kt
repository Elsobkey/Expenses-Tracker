package com.sobky.expensestracking.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.sobky.expensestracking.R

fun updateStatusBarColor(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // status bar with background white and black icons applied only on api 23 (M) and higher
        // if api less than android M (api 23) the white background will override icons to be
        // transparent and this is not applicable so set background to red.
        val color: Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                R.color.color_white
            else R.color.color_red
        val window = (context as Activity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(
            context, color
        )
    }
}

fun toolbarConfig(context: Context) {
    val mToolbar: Toolbar = (context as Activity).findViewById<View>(R.id.toolbar) as Toolbar
    (context as AppCompatActivity).setSupportActionBar(mToolbar)
    val actionBar: ActionBar? = context.supportActionBar
    if (actionBar != null) {
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowTitleEnabled(false)
    }

}

