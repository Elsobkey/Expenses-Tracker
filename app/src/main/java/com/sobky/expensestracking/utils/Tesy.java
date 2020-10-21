package com.sobky.expensestracking.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Tesy {

    Tesy() {

        List<String> ss = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Log.v("TAG", ss.get(i).toString());
        }
    }
}
