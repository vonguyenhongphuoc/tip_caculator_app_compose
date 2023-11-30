package com.devhp.firstcompose.util

import android.content.Context
import android.widget.Toast

object MyToast {
    private var currentToast: Toast? = null
    fun showToast(context: Context, message: String) {
        currentToast?.cancel()
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }
}