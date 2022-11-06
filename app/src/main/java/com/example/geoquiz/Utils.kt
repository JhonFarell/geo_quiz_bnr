package com.example.geoquiz

import android.content.Context
import android.widget.Toast

class Utils {
    fun makeToast (context: Context, text: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}