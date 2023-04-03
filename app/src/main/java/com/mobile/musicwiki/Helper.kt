package com.mobile.musicwiki

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

object Helper {
    fun Context.showToast(message: String = "Something went wrong") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.onBackPressed() {
        this.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun getSliced(input: String): String {
        return input.substring(0, input.indexOf("<"))
    }
}