/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sobky.expensestracking.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

//@BindingAdapter("android:text")
//fun formatText(editText: TextInputEditText, number: Double) {
//    //editText.setText(number.toString())
//    if (number <= 0) {
//        editText.setText("0")
//    } else {
//        if (number % 1 == 0.0) { // then it is integer number
//            editText.setText(number.toInt().toString())
//        } else {
//            editText.setText(roundNumber(number, 2).toString())
//        }
//    }
//}
//
//
//
//@BindingAdapter("android:text")
//fun formatText(editText: TextInputEditText?, text: String?) {
//    if (!text.isNullOrEmpty())
//        editText?.setText(text)
//}

@BindingAdapter("android:text")
fun bindEditText(editText: TextInputEditText?, value: String?) {
    if (value.isNullOrEmpty()) {
        editText?.setText("")
    } else {
        if (editText?.text.toString() != value.toString()) {
            editText?.setText(value)
        }
    }
}


//@BindingAdapter("android:text")
//fun setAmount(editText: EditText, currency: Float?) {
//    if (currency != null && currency != 0.0f) {
//
//        editText.setText(String.format("%.8f", currency))
//    } else {
//        editText.setText("")
//    }
//}
