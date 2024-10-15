package com.blockstream.green.extensions


import android.app.Activity
import android.content.Context
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar


fun ViewBinding.context(): Context = root.context

fun Activity.snackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(findViewById(android.R.id.content), text, duration).show()
}